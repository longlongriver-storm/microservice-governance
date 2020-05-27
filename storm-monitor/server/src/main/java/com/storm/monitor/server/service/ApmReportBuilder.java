/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.server.service;

import com.storm.monitor.core.common.NamedThreadFactory;
import com.storm.monitor.server.model.DaoMonitorLog;
import com.storm.monitor.server.model.DaoMonitorLogDay;
import com.storm.monitor.server.model.DaoMonitorLogHour;
import com.storm.monitor.server.model.ServiceMonitorLog;
import com.storm.monitor.server.model.ServiceMonitorLogDay;
import com.storm.monitor.server.model.ServiceMonitorLogHour;
import com.storm.monitor.server.model.view.DaoMonitorLogDayView;
import com.storm.monitor.server.model.view.DaoMonitorLogHourView;
import com.storm.monitor.server.model.view.DaoMonitorLogView;
import com.storm.monitor.server.model.view.ServiceMonitorLogDayView;
import com.storm.monitor.server.model.view.ServiceMonitorLogHourView;
import com.storm.monitor.server.model.view.ServiceMonitorLogView;
import com.storm.monitor.core.util.MilliSecondTimer;
import com.storm.monitor.core.common.Common;
import com.storm.monitor.server.receiver.TcpSocketReceiver;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 汇总统计数据生成器（生成小时汇总数据，天汇总数据）
 *
 * @author lixin
 */
@Service
public class ApmReportBuilder implements InitializingBean {

    private boolean isReceiveMessage = false;
    private boolean isBuildReport = false;

    /**
     * 普通日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(ApmReportBuilder.class);

    private static final Map<String, String> apiNames = new HashMap();
    /**
     * 报表生成任务的启动间隔时间10分钟汇总一次
     */
    private static final long LOG_INTERVAL_DELAY = 1000L * 60L * 10L;

    private static final long ONE_MINUTE_INTERVAL = 1000L * 60L;
    /**
     * 定时检查
     */
    private ScheduledExecutorService scheduledThreadPool;
    private ScheduledExecutorService scheduledReplaceServiceNameThreadPool;

    @Autowired
    ServiceMonitorLogDayService serviceMonitorLogDayService;

    @Autowired
    ServiceMonitorLogHourService serviceMonitorLogHourService;

    @Autowired
    ServiceMonitorLogService serviceMonitorLogService;

    @Autowired
    DaoMonitorLogDayService daoMonitorLogDayService;

    @Autowired
    DaoMonitorLogHourService daoMonitorLogHourService;

    @Autowired
    DaoMonitorLogService daoMonitorLogService;

    @Autowired
    HashMap<String, String> apmCommonConfig;

    // TODO code application logic here
    final TcpSocketReceiver messageReceiver = new TcpSocketReceiver();
    //用于展示消息接收服务端的处理概括信息，用了JDK自带的HTTP服务
    final HttpWebServer httpWebServer = new HttpWebServer();

    /**
     * 统计信息发送器（将统计信息发送出去）
     */
    private class StaticsDataSender implements Runnable {

        @Override
        public void run() {
            try {
                long t1 = MilliSecondTimer.currentTimeMillis();

                //统计服务小时报表、
                createServiceReportHour();
                //统计DAO小时报表、
                createDaoReportHour();
                //统计服务天报表
                createServiceReportDay();
                //统计DAO天报表
                createDaoReportDay();
            } catch (Exception e) {
                LOG.error("StaticsDataSender is error,{}", e);
            }
        }
    }

    /**
     * 创建小时报表
     */
    private void createServiceReportHour() throws Exception {
        long now_time = MilliSecondTimer.currentTimeMillis();
        //获得小时记录表中最新的一个时间
        ServiceMonitorLogHourView serviceMonitorLogHourView = new ServiceMonitorLogHourView();
        serviceMonitorLogHourView.getPage().setSortColumn("log_time");
        serviceMonitorLogHourView.getPage().setOrderBy("desc");
        serviceMonitorLogHourView.getPage().setBegin(0);
        serviceMonitorLogHourView.getPage().setEnd(1);
        List<ServiceMonitorLogHour> newestLogHours = serviceMonitorLogHourService.queryServiceMonitorLogHourByPage(serviceMonitorLogHourView);
        Date newestLogHour = null;  //开始小时
        if (newestLogHours != null && !newestLogHours.isEmpty()) {
            newestLogHour = newestLogHours.get(0).getLogTime();
            newestLogHour = DateUtils.addHours(newestLogHour, 1);   //计算已有小时报表下一个小时开始的统计数据
        } else {
            //从原始分钟日志记录表中找数据
            ServiceMonitorLogView serviceMonitorLogView = new ServiceMonitorLogView();
            serviceMonitorLogView.getPage().setSortColumn("log_time");
            serviceMonitorLogView.getPage().setOrderBy("asc");
            serviceMonitorLogView.getPage().setBegin(0);
            serviceMonitorLogView.getPage().setEnd(1);
            List<ServiceMonitorLog> newestLogs = serviceMonitorLogService.queryServiceMonitorLogByPage(serviceMonitorLogView);
            if (newestLogs != null && !newestLogs.isEmpty()) {
                newestLogHour = newestLogs.get(0).getLogTime();  //统计原始分钟报表中的那个小时的统计数据
                newestLogHour = new Date(((long) (newestLogHour.getTime() / DateUtils.MILLIS_PER_HOUR)) * DateUtils.MILLIS_PER_HOUR);
            }
        }
        if (newestLogHour == null) {
            //找不到开始时间，说明没有数据只能退出
            return;
        }
        //一个小时一个小时的遍历，一直遍历到当前时间
        Date now_hour = new Date(((long) ((new Date()).getTime() / DateUtils.MILLIS_PER_HOUR)) * DateUtils.MILLIS_PER_HOUR);
        while (newestLogHour.before(now_hour)) {
            //System.out.println("Myreportlog,hour"+DateUtil.formatDate(newestLogHour, "yyyy-MM-dd HH:mm:ss"));
            //如果下一小时的原始分钟数据已经有了，则说明当前小时的数据已经完备，可以做统计了
            ServiceMonitorLogView serviceMonitorLogView = new ServiceMonitorLogView();
            serviceMonitorLogView.setLogTimeBegin(DateUtils.addHours(newestLogHour, 1));
            serviceMonitorLogView.getPage().setBegin(0);
            serviceMonitorLogView.getPage().setEnd(1);
            List<ServiceMonitorLog> newestLogs = serviceMonitorLogService.queryServiceMonitorLogByPage(serviceMonitorLogView);
            if (newestLogs == null || newestLogs.isEmpty()) {
                break;  //说明后面没有数据了，不需要再循环统计
            }

            //先删除
            int deleteNum = serviceMonitorLogHourService.deleteServiceMonitorLogHourById(newestLogHour);
            //在添加
            Date logEndTime = DateUtils.addMinutes(newestLogHour, 59);
            //修正类名问题，替换$Proxy.的类名
            //updateServiceName(newestLogHour,logEndTime);
            int insertNum = serviceMonitorLogHourService.addServiceMonitorLogHourBySelect(newestLogHour, newestLogHour, logEndTime);
            LOG.info("service-reportlog,小时时间点{},先删除了{}条数据,插入{}条汇总数据", df(newestLogHour), deleteNum, insertNum);

            newestLogHour = DateUtils.addHours(newestLogHour, 1);
        }
    }

    /**
     * 创建服务天报表
     */
    private void createServiceReportDay() throws Exception {
        long now_time = MilliSecondTimer.currentTimeMillis();
        //获得天记录表中最新的一个时间
        ServiceMonitorLogDayView serviceMonitorLogDayView = new ServiceMonitorLogDayView();
        serviceMonitorLogDayView.getPage().setSortColumn("log_time");
        serviceMonitorLogDayView.getPage().setOrderBy("desc");
        serviceMonitorLogDayView.getPage().setBegin(0);
        serviceMonitorLogDayView.getPage().setEnd(1);
        List<ServiceMonitorLogDay> newestLogDays = serviceMonitorLogDayService.queryServiceMonitorLogDayByPage(serviceMonitorLogDayView);
        Date newestLogDay = null;  //开始小时
        if (newestLogDays != null && !newestLogDays.isEmpty()) {
            newestLogDay = newestLogDays.get(0).getLogTime();
            newestLogDay = DateUtils.addDays(newestLogDay, 1);   //计算已有天报表下一天开始的统计数据
        } else {
            //从小时日志记录表中找数据
            ServiceMonitorLogHourView serviceMonitorLogHourView = new ServiceMonitorLogHourView();
            serviceMonitorLogHourView.getPage().setSortColumn("log_time");
            serviceMonitorLogHourView.getPage().setOrderBy("asc");
            serviceMonitorLogHourView.getPage().setBegin(0);
            serviceMonitorLogHourView.getPage().setEnd(1);
            List<ServiceMonitorLogHour> newestLogs = serviceMonitorLogHourService.queryServiceMonitorLogHourByPage(serviceMonitorLogHourView);
            if (newestLogs != null && !newestLogs.isEmpty()) {
                newestLogDay = newestLogs.get(0).getLogTime();  //统计小时报表中的那天的统计数据
                newestLogDay = new Date(((long) (newestLogDay.getTime() / DateUtils.MILLIS_PER_DAY)) * DateUtils.MILLIS_PER_DAY);
            }
        }
        if (newestLogDay == null) {
            //找不到开始时间，说明没有数据只能退出
            return;
        }
        //一天一天的遍历，一直遍历到当前时间
        Date now_day = new Date(((long) ((new Date()).getTime() / DateUtils.MILLIS_PER_DAY)) * DateUtils.MILLIS_PER_DAY);
        while (newestLogDay.before(now_day)) {
            //System.out.println("Myreportlog,day"+DateUtil.formatDate(newestLogDay, "yyyy-MM-dd HH:mm:ss"));
            //如果下一天的小时统计数据已经有了，则说明当前天的数据已经完备，可以做统计了
            ServiceMonitorLogHourView serviceMonitorLogHourView = new ServiceMonitorLogHourView();
            serviceMonitorLogHourView.setLogTimeBegin(DateUtils.addDays(newestLogDay, 1));
            serviceMonitorLogHourView.getPage().setBegin(0);
            serviceMonitorLogHourView.getPage().setEnd(1);
            List<ServiceMonitorLogHour> newestLogs = serviceMonitorLogHourService.queryServiceMonitorLogHourByPage(serviceMonitorLogHourView);
            if (newestLogs == null || newestLogs.isEmpty()) {
                break;  //说明后面没有数据了，不需要再循环统计
            }
            //先删除
            int deleteNum = serviceMonitorLogDayService.deleteServiceMonitorLogDayById(newestLogDay);
            //在添加
            Date logEndTime = DateUtils.addHours(newestLogDay, 23);
            int insertNum = serviceMonitorLogDayService.addServiceMonitorLogDayBySelect(newestLogDay, newestLogDay, logEndTime);
            LOG.info("service-reportlog,天时间点{},先删除了{}条数据,插入{}条汇总数据", df(newestLogDay), deleteNum, insertNum);

            newestLogDay = DateUtils.addDays(newestLogDay, 1);
        }
    }

    /**
     * 创建小时天报表
     */
    private void createDaoReportHour() throws Exception {
        long now_time = MilliSecondTimer.currentTimeMillis();
        //获得小时记录表中最新的一个时间
        DaoMonitorLogHourView daoMonitorLogHourView = new DaoMonitorLogHourView();
        daoMonitorLogHourView.getPage().setSortColumn("log_time");
        daoMonitorLogHourView.getPage().setOrderBy("desc");
        daoMonitorLogHourView.getPage().setBegin(0);
        daoMonitorLogHourView.getPage().setEnd(1);
        List<DaoMonitorLogHour> newestLogHours = daoMonitorLogHourService.queryDaoMonitorLogHourByPage(daoMonitorLogHourView);
        Date newestLogHour = null;  //开始小时
        if (newestLogHours != null && !newestLogHours.isEmpty()) {
            newestLogHour = newestLogHours.get(0).getLogTime();
            newestLogHour = DateUtils.addHours(newestLogHour, 1);   //计算已有小时报表下一个小时开始的统计数据
        } else {
            //从原始分钟日志记录表中找数据
            DaoMonitorLogView daoMonitorLogView = new DaoMonitorLogView();
            daoMonitorLogView.getPage().setSortColumn("log_time");
            daoMonitorLogView.getPage().setOrderBy("asc");
            daoMonitorLogView.getPage().setBegin(0);
            daoMonitorLogView.getPage().setEnd(1);
            List<DaoMonitorLog> newestLogs = daoMonitorLogService.queryDaoMonitorLogByPage(daoMonitorLogView);
            if (newestLogs != null && !newestLogs.isEmpty()) {
                newestLogHour = newestLogs.get(0).getLogTime();  //统计原始分钟报表中的那个小时的统计数据
                newestLogHour = new Date(((long) (newestLogHour.getTime() / DateUtils.MILLIS_PER_HOUR)) * DateUtils.MILLIS_PER_HOUR);
            }
        }
        if (newestLogHour == null) {
            //找不到开始时间，说明没有数据只能退出
            return;
        }
        //一个小时一个小时的遍历，一直遍历到当前时间
        Date now_hour = new Date(((long) ((new Date()).getTime() / DateUtils.MILLIS_PER_HOUR)) * DateUtils.MILLIS_PER_HOUR);
        while (newestLogHour.before(now_hour)) {
            //System.out.println("Myreportlog,hour"+DateUtil.formatDate(newestLogHour, "yyyy-MM-dd HH:mm:ss"));
            //如果下一小时的原始分钟数据已经有了，则说明当前小时的数据已经完备，可以做统计了
            DaoMonitorLogView daoMonitorLogView = new DaoMonitorLogView();
            daoMonitorLogView.setLogTimeBegin(DateUtils.addHours(newestLogHour, 1));
            daoMonitorLogView.getPage().setBegin(0);
            daoMonitorLogView.getPage().setEnd(1);
            List<DaoMonitorLog> newestLogs = daoMonitorLogService.queryDaoMonitorLogByPage(daoMonitorLogView);
            if (newestLogs == null || newestLogs.isEmpty()) {
                break;  //说明后面没有数据了，不需要再循环统计
            }
            //先删除
            int deleteNum = daoMonitorLogHourService.deleteDaoMonitorLogHourById(newestLogHour);
            //在添加
            Date logEndTime = DateUtils.addMinutes(newestLogHour, 59);
            int insertNum = daoMonitorLogHourService.addDaoMonitorLogHourBySelect(newestLogHour, newestLogHour, logEndTime);
            LOG.info("dao-reportlog,小时时间点{},先删除了{}条数据,插入{}条汇总数据", df(newestLogHour), deleteNum, insertNum);

            newestLogHour = DateUtils.addHours(newestLogHour, 1);
        }
    }

    /**
     * 创建DAO天报表
     */
    private void createDaoReportDay() throws Exception {
        long now_time = MilliSecondTimer.currentTimeMillis();
        //获得天记录表中最新的一个时间
        DaoMonitorLogDayView daoMonitorLogDayView = new DaoMonitorLogDayView();
        daoMonitorLogDayView.getPage().setSortColumn("log_time");
        daoMonitorLogDayView.getPage().setOrderBy("desc");
        daoMonitorLogDayView.getPage().setBegin(0);
        daoMonitorLogDayView.getPage().setEnd(1);
        List<DaoMonitorLogDay> newestLogDays = daoMonitorLogDayService.queryDaoMonitorLogDayByPage(daoMonitorLogDayView);
        Date newestLogDay = null;  //开始小时
        if (newestLogDays != null && !newestLogDays.isEmpty()) {
            newestLogDay = newestLogDays.get(0).getLogTime();
            newestLogDay = DateUtils.addDays(newestLogDay, 1);   //计算已有天报表下一天开始的统计数据
        } else {
            //从小时日志记录表中找数据
            DaoMonitorLogHourView daoMonitorLogHourView = new DaoMonitorLogHourView();
            daoMonitorLogHourView.getPage().setSortColumn("log_time");
            daoMonitorLogHourView.getPage().setOrderBy("asc");
            daoMonitorLogHourView.getPage().setBegin(0);
            daoMonitorLogHourView.getPage().setEnd(1);
            List<DaoMonitorLogHour> newestLogs = daoMonitorLogHourService.queryDaoMonitorLogHourByPage(daoMonitorLogHourView);
            if (newestLogs != null && !newestLogs.isEmpty()) {
                newestLogDay = newestLogs.get(0).getLogTime();  //统计小时报表中的那天的统计数据
                newestLogDay = new Date(((long) (newestLogDay.getTime() / DateUtils.MILLIS_PER_DAY)) * DateUtils.MILLIS_PER_DAY);
            }
        }
        if (newestLogDay == null) {
            //找不到开始时间，说明没有数据只能退出
            return;
        }
        //一天一天的遍历，一直遍历到当前时间
        Date now_day = new Date(((long) ((new Date()).getTime() / DateUtils.MILLIS_PER_DAY)) * DateUtils.MILLIS_PER_DAY);
        while (newestLogDay.before(now_day)) {
            //System.out.println("Myreportlog,day"+DateUtil.formatDate(newestLogDay, "yyyy-MM-dd HH:mm:ss"));
            //如果下一天的小时统计数据已经有了，则说明当前天的数据已经完备，可以做统计了
            DaoMonitorLogHourView daoMonitorLogHourView = new DaoMonitorLogHourView();
            daoMonitorLogHourView.setLogTimeBegin(DateUtils.addDays(newestLogDay, 1));
            daoMonitorLogHourView.getPage().setBegin(0);
            daoMonitorLogHourView.getPage().setEnd(1);
            List<DaoMonitorLogHour> newestLogs = daoMonitorLogHourService.queryDaoMonitorLogHourByPage(daoMonitorLogHourView);
            if (newestLogs == null || newestLogs.isEmpty()) {
                break;  //说明后面没有数据了，不需要再循环统计
            }
            //先删除
            int deleteNum = daoMonitorLogDayService.deleteDaoMonitorLogDayById(newestLogDay);
            //在添加
            Date logEndTime = DateUtils.addHours(newestLogDay, 23);
            int insertNum = daoMonitorLogDayService.addDaoMonitorLogDayBySelect(newestLogDay, newestLogDay, logEndTime);
            LOG.info("dao-reportlog,天时间点{},先删除了{}条数据,插入{}条汇总数据", df(newestLogDay), deleteNum, insertNum);

            newestLogDay = DateUtils.addDays(newestLogDay, 1);
        }
    }

    private String df(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * 关闭APM的接收服务
     */
    public void shutdownApmReceiveServer() {
        messageReceiver.destory();
        httpWebServer.stopHttpServer();
    }

    /**
     * 启动APM的接收服务
     */
    public void startApmReceiveServer(int port) {

        //消息接收服务器初始化
        messageReceiver.init(port);
        //该方法用来在jvm中增加一个关闭的钩子。当程序正常退出,系统调用 System.exit方法或虚拟机被关闭时才会执行添加的shutdownHook线程。
        //其中shutdownHook是一个已初始化但并不有启动的线程，当jvm关闭的时候，会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子，
        //当系统执行完这些钩子后，jvm才会关闭。所以可通过这些钩子在jvm关闭的时候进行内存清理、资源回收等工作。  
        Runtime.getRuntime().addShutdownHook(new Thread() {
            /**
             * JVM退出之前，必须关闭NIO相关通道资源
             */
            @Override
            public void run() {
                messageReceiver.destory();
            }
        });

        //如果指定了HTTP端口，则启动HTTP服务
        try {
            String httpPort = apmCommonConfig.get(Common.SPRING_KEY_SERVER_CONFIG_HTTP_LISTEN_PORT);
            if (httpPort != null && httpPort.trim().length() > 0) {
                httpWebServer.startHttpServer(Integer.valueOf(httpPort), this);
                LOG.info("***************************************************************************************");
                LOG.info("             Accept Server HTTP Listening Port:" + httpPort);
                LOG.info("***************************************************************************************");
            }

        } catch (Exception ex) {
            LOG.info("start HTTP Server error!,because:{}", ex.getMessage());
        }

    }

    /**
     * 获取APM消息处理的基本信息
     *
     * @return
     */
    public String getApmServerInfo() {
        return messageReceiver.getInfo();
    }

    /**
     * 启动定时器，
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (apmCommonConfig != null) {
            if ("true".equalsIgnoreCase(apmCommonConfig.get(Common.SPRING_KEY_SERVER_CONFIG_BUILD_REPORT))) {
                isBuildReport = true;
            }
            if ("true".equalsIgnoreCase(apmCommonConfig.get(Common.SPRING_KEY_SERVER_CONFIG_RECEIVE_MESSAGE))) {
                isReceiveMessage = true;
            }
        }

        //****************************
        //     启动APM的消息接收服务
        //****************************
        if (isReceiveMessage) {
            int port = Integer.valueOf(apmCommonConfig.get(Common.SPRING_KEY_SERVER_CONFIG_LISTEN_PORT));
            LOG.info("***************************************************************************************");
            LOG.info("             This Is A Message Accept Server,Listening Port:" + port);
            LOG.info("***************************************************************************************");

            startApmReceiveServer(port);
        }

        //****************************
        //     启动报表服务
        //****************************
        if (isBuildReport) {
            LOG.info("***************************************************************************************");
            LOG.info("             This IS A APM Report Build Server");
            LOG.info("***************************************************************************************");
            long start_t = System.currentTimeMillis();

            long t1 = MilliSecondTimer.currentTimeMillis();
            long init_delay = t1 % ONE_MINUTE_INTERVAL;
            init_delay = ONE_MINUTE_INTERVAL - init_delay;
            //启动定时线程池
            scheduledThreadPool = Executors.newScheduledThreadPool(1, new NamedThreadFactory("APM-Report-schedule", true));
            scheduledThreadPool.scheduleWithFixedDelay(new StaticsDataSender(), init_delay + 10000L, LOG_INTERVAL_DELAY, TimeUnit.MILLISECONDS);
            //scheduledThreadPool.scheduleWithFixedDelay(new StaticsDataSender(), 10000L, LOG_INTERVAL_DELAY, TimeUnit.MILLISECONDS);
            //initServiceName();
            scheduledReplaceServiceNameThreadPool = Executors.newScheduledThreadPool(1, new NamedThreadFactory("APM-ReplaceServicezname-schedule", true));
            LOG.info("APM Report Build Task started!");
        }

    }

}
