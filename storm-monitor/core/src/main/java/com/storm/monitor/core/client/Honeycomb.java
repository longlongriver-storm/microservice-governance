/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.client;

import com.storm.monitor.core.client.collector.SystemStatusInfoCollector;
import com.storm.monitor.core.entity.EventStatistics;
import com.storm.monitor.core.entity.EventMessage;
import com.storm.monitor.core.common.NamedThreadFactory;
import com.storm.monitor.core.util.MilliSecondTimer;
import com.storm.monitor.core.common.Common;
import com.storm.monitor.core.entity.DefaultMessageTree;
import com.storm.monitor.core.entity.MessageTree;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志集中处理服务
 *
 * @author lixin
 */
public class Honeycomb {

    /**
     * 普通日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(Honeycomb.class);

    /**
     * 服务接口监控日志
     */
    private static final Logger LOG_SERVICE_MONITOR = LoggerFactory.getLogger("APM-SERVICE-MONITOR");
    /**
     * DAO接口监控日志
     */
    private static final Logger LOG_DAO_MONITOR = LoggerFactory.getLogger("APM-DAO-MONITOR");
    /**
     * 错误日志明细
     */
    private static final Logger LOG_ERROR_MONITOR = LoggerFactory.getLogger("APM-ERROR-MONITOR");
    /**
     * 自定义采集日志明细 TODO:在log4j.xml中新增一个appender
     */
    private static final Logger LOG_CUSTOM_DATA_PICK_MONITOR = LoggerFactory.getLogger("APM-CUSTOM-DATA-PICK-MONITOR");

    /**
     * 接收消息队列大小
     */
    public static final int SIZE = 3000;

    /**
     * 访问监控数据采集任务线程启动间隔
     */
    private static final long LOG_INTERVAL_DELAY = 1000L * 60L;

    private volatile boolean m_active;
    /**
     * 事件队列
     */
    private BlockingQueue<EventMessage> eventQueue;

    private ScheduledExecutorService scheduledThreadPool;
    /**
     * 记录失败的事件消息数量（失败是由于消息队列满了）
     */
    private AtomicInteger m_errors;

    //共享线程池，线程池采用固定大小线程池
    private ExecutorService fixThreadPool;

    /**
     * 服务调用统计数据
     */
    private ConcurrentHashMap<Long, StaticsMapManager> serviceStatisticsManagers;
    /**
     * dao调用统计数据
     */
    private ConcurrentHashMap<Long, StaticsMapManager> daoStatisticsManagers;

    /**
     * 发送器
     */
    private TcpSocketSender sender;
    /**
     * 单实例
     */
    private static volatile Honeycomb instance;
    /**
     * 日志存储模式，默认本地日志存储，在init方法中再加载配置确定
     */
    private volatile boolean isLogStoreLocal = true;

    private Honeycomb() {
        init();
    }

    public static Honeycomb getIstance() {
        // 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
        if (instance == null) {
            //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
            synchronized (Honeycomb.class) {
                //未初始化，则初始instance变量
                if (instance == null) {
                    instance = new Honeycomb();
                }
            }
        }
        return instance;
    }

    /**
     * 消息处理线程
     */
    private class EventConsumerTask implements Runnable {

        @Override
        public void run() {
            int nullIdx = 0;
            while (true) {
                try {
                    //从消息队列中获取一个消息
                    EventMessage event = eventQueue.poll();

                    if (event != null) {
                        long c_t1 = event.getTimeInMillis() / Common.ONE_MINUTE_INTERVAL;   //当前分钟
                        StaticsMapManager sm = null;
                        if (event.getType() == EventMessage.TYPE_SERVICE_METHOD) {
                            sm = serviceStatisticsManagers.get(c_t1);  //当前分钟对应的服务调用统计器
                        } else {   //DAO
                            sm = daoStatisticsManagers.get(c_t1);        //当前分钟对应的DAO调用统计器
                        }
                        sm.addEventMessage(event);  //消息处理
                        event = null;
                        nullIdx = 0;
                    } else {   //消息队列为空
                        nullIdx++;
                        if (nullIdx >= 5) {
                            nullIdx = 0;
                            Thread.sleep(5);  //连续5次取不到消息，则线程睡眠5毫秒
                        }
                    }
                } catch (Exception e) {
                    LOG.error("EventConsumerTask is error,{}", e);
                }
            }

        }
    }

    /**
     * 统计信息发送器（将统计信息发送出去）
     */
    private class StaticsDataSender implements Runnable {

        @Override
        public void run() {
            try {
                long t1 = MilliSecondTimer.currentTimeMillis();
                //初始化后面连续5分钟的统计计数管理器
                addNextFiveMinuteStaticsManage(t1);
                long t2 = t1 / Common.ONE_MINUTE_INTERVAL;
                long[] preMinutes = new long[]{t2 - 3, t2 - 2, t2 - 1};
                SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat format_time = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                //*******************************************
                //          处理服务接口调用
                //*******************************************
                for (long pt1 : preMinutes) {
                    if (!serviceStatisticsManagers.containsKey(pt1)) {
                        continue;
                    }
                    StaticsMapManager sm = serviceStatisticsManagers.get(pt1);
                    //LOG_SERVICE_MONITOR.info("################################################ Service monitor log {},thread start time:{} ################################################", format_date.format(new Date(pt1 * ONE_MINUTE_INTERVAL)), format_date.format(new Date(t1)));
                    for (String claAndMth : sm.statsMap.keySet()) {
                        EventStatistics st = sm.statsMap.get(claAndMth);
                        boolean isRemoteSenderOK=false;
                        if(!isLogStoreLocal){     //***远程收集***
                            MessageTree msg = buildMessageTree(MessageTree.MESSAGE_TYPE_SERVICE);
                            msg.setMessage(String.format("%s,%s,success=%s,failure=%s,bizFailure=%s,avgElapsed=%s,maxElapsed=%s,minElapsed=%s,lastestBizErrorCode=%s,lastestErrorMsg=%s,lastestBizErrorMsg=%s;",
                                String.valueOf(pt1 * Common.ONE_MINUTE_INTERVAL),
                                claAndMth,
                                st.getSuccess(),
                                st.getFailure(),
                                st.getBizFailure(),
                                st.getSuccess() == 0 ? 0 : (st.getAllElapsed() / st.getSuccess()),
                                st.getMaxElapsed(),
                                st.getMinElapsed(),
                                st.getLastestBizErrorCode() == null ? "" : st.getLastestBizErrorCode(),
                                st.getLastestErrorMsg() == null ? "" : st.getLastestErrorMsg(),
                                st.getLastestBizErrorMsg() == null ? "" : st.getLastestBizErrorMsg()
                            ));
                            isRemoteSenderOK=sender.send(msg);
                        }
                        if (isLogStoreLocal || isRemoteSenderOK==false) {    //***本地存储/远程发送失败***
                            LOG_SERVICE_MONITOR.info("{},{},success={},failure={},bizFailure={},avgElapsed={},maxElapsed={},minElapsed={},lastestBizErrorCode={},lastestErrorMsg={},lastestBizErrorMsg={};",
                                format_time.format(pt1 * Common.ONE_MINUTE_INTERVAL),
                                claAndMth,
                                st.getSuccess(),
                                st.getFailure(),
                                st.getBizFailure(),
                                st.getSuccess() == 0 ? 0 : (st.getAllElapsed() / st.getSuccess()),
                                st.getMaxElapsed(),
                                st.getMinElapsed(),
                                st.getLastestBizErrorCode() == null ? "" : st.getLastestBizErrorCode(),
                                st.getLastestErrorMsg() == null ? "" : st.getLastestErrorMsg(),
                                st.getLastestBizErrorMsg() == null ? "" : st.getLastestBizErrorMsg()
                            );
                        } 
                    }
                    serviceStatisticsManagers.remove(pt1);   //移除这个分钟的统计信息
                    sm.clearAll();
                    sm = null;

                }
                //*******************************************
                //              处理dao接口调用
                //*******************************************
                for (long pt1 : preMinutes) {
                    if (!daoStatisticsManagers.containsKey(pt1)) {
                        continue;
                    }
                    StaticsMapManager sm = daoStatisticsManagers.get(pt1);
                    //LOG_DAO_MONITOR.info("################################################ DAO monitor log {},thread start time:{} ################################################", format_date.format(new Date(pt1 * ONE_MINUTE_INTERVAL)), format_date.format(new Date(t1)));
                    for (String claAndMth : sm.statsMap.keySet()) {
                        EventStatistics st = sm.statsMap.get(claAndMth);
                        boolean isRemoteSenderOK=false;
                        if(!isLogStoreLocal){
                            MessageTree msg = buildMessageTree(MessageTree.MESSAGE_TYPE_DAO);
                            msg.setMessage(String.format("%s,%s,success=%s,failure=%s,avgElapsed=%s,maxElapsed=%s,minElapsed=%s,lastestErrorMsg=%s;",
                                String.valueOf(pt1 * Common.ONE_MINUTE_INTERVAL),
                                claAndMth,
                                st.getSuccess(),
                                st.getFailure(),
                                st.getSuccess() == 0 ? 0 : (st.getAllElapsed() / st.getSuccess()),
                                st.getMaxElapsed(),
                                st.getMinElapsed(),
                                st.getLastestErrorMsg() == null ? "" : st.getLastestErrorMsg()
                            ));
                            isRemoteSenderOK=sender.send(msg);
                        }
                        
                        if (isLogStoreLocal || isRemoteSenderOK==false) {   //***本地日志存储/远程发送失败***
                            LOG_DAO_MONITOR.info("{},{},success={},failure={},avgElapsed={},maxElapsed={},minElapsed={},lastestErrorMsg={};",
                                format_time.format(pt1 * Common.ONE_MINUTE_INTERVAL),
                                claAndMth,
                                st.getSuccess(),
                                st.getFailure(),
                                st.getSuccess() == 0 ? 0 : (st.getAllElapsed() / st.getSuccess()),
                                st.getMaxElapsed(),
                                st.getMinElapsed(),
                                st.getLastestErrorMsg() == null ? "" : st.getLastestErrorMsg()
                            );
                        } 
                    }
                    daoStatisticsManagers.remove(pt1);   //移除这个分钟的统计信息
                    sm.clearAll();
                    sm = null;

                }
            } catch (Exception e) {
                LOG.error("StaticsDataSender is error,{}", e);
            }
        }
    }

    /**
     * 统计计数管理器(1分钟的)
     */
    private class StaticsMapManager {

        Map<String, EventStatistics> statsMap = new HashMap();
        SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public void addEventMessage(EventMessage event) {
            String eventKey = getEventKey(event);

            EventStatistics stats = null;
            if (statsMap.containsKey(eventKey)) {
                stats = statsMap.get(eventKey);
            } else {
                stats = new EventStatistics();
                statsMap.put(eventKey, stats);
            }
            if (event.getCallStatus() == EventMessage.CALL_STATUS_SUCCESS) {    //调用成功
                stats.setSuccess(stats.getSuccess() + 1);                          //成功数量
                stats.setAllElapsed(stats.getAllElapsed() + event.getElapsed());   //总调用延时
                if (stats.getMaxElapsed() < event.getElapsed()) {
                    stats.setMaxElapsed(event.getElapsed());                 //最大耗时
                }
                if (stats.getMinElapsed() > event.getElapsed()) {
                    stats.setMinElapsed(event.getElapsed());                 //最小耗时
                }
                if (event.getBizMessage() != null) {                             //业务错误（目前是只要有业务消息就一定是业务错误，TODO:**未来这个逻辑不一定成立**）
                    stats.setBizFailure(stats.getBizFailure() + 1);            //业务错误总数量
                    stats.setLastestBizErrorCode(event.getBizMessage().getBizErrCode());    //最新业务错误代码
                    stats.setLastestBizErrorMsg(event.getBizMessage().getBizErrMsg());      //最新业务错误消息
                    logError(event, true);                                                   //记录错误明细
                }
            } else {           //系统错误
                stats.setFailure(stats.getFailure() + 1);                          //失败数量
                stats.setLastestErrorMsg(event.getErrorType());                    //最新一个错误异常类名称
                logError(event, false);                                             //记录错误明细
            }

            //自定义数据抓取
            if (event.getInfos() != null) {
                logCustomDataPick(event);
            }
        }

        public void clearAll() {
            statsMap.clear();
        }

        /**
         * 构建事件的key，如果是服务，则类名+方法名；如果是DAO，则直接使用方法名
         *
         * @param event
         * @return
         */
        private String getEventKey(EventMessage event) {
            String eventKey = null;
            if (event.getType() == EventMessage.TYPE_SERVICE_METHOD) {
                eventKey = event.getClazzName() + "." + event.getMethodName();
            } else {        //DAO
                eventKey = event.getMethodName();
            }
            return eventKey;
        }

        /**
         * 传输自定义采集数据
         *
         * @param event
         */
        private void logCustomDataPick(EventMessage event) {
            String eventKey = getEventKey(event);
            boolean isRemoteSenderOK=false;
            if(!isLogStoreLocal){
                MessageTree msg = buildMessageTree(MessageTree.MESSAGE_TYPE_CUSTOM_PICK);
                msg.setMessage(String.format("%s,%s,traceId=%s,userKey=%s,customPickData=%s;",
                    String.valueOf(event.getTimeInMillis()), //事件时间
                    eventKey, //接口名称
                    event.getTraceId() == null ? "" : event.getTraceId(), //调用链跟踪ID
                    event.getUserKey() == null ? "" : event.getUserKey(), //用户标识                       
                    event.getInfos().toString()
                ));
                isRemoteSenderOK=sender.send(msg);
            }
            
            if (isLogStoreLocal || isRemoteSenderOK==false) {   //***本地日志存储/远程发送失败***
                LOG_CUSTOM_DATA_PICK_MONITOR.info("{},{},traceId={},userKey={},customPickData={};",
                    format_date.format(event.getTimeInMillis()), //事件时间
                    eventKey, //接口名称
                    event.getTraceId() == null ? "" : event.getTraceId(), //调用链跟踪ID
                    event.getUserKey() == null ? "" : event.getUserKey(), //用户标识                       
                    event.getInfos().toString()
                );
            }
        }

        private void logError(EventMessage event, boolean isBiz) {
            String eventKey = getEventKey(event);
            boolean isRemoteSenderOK=false;
            if(!isLogStoreLocal){
                MessageTree msg = buildMessageTree(MessageTree.MESSAGE_TYPE_ERROR);
                msg.setMessage(String.format("%s,%s,%s,traceId=%s,userKey=%s,errCode=%s,errMsg=%s;",
                    String.valueOf(event.getTimeInMillis()), //事件时间
                    eventKey, //接口名称
                    isBiz ? "1" : "0", //1：业务错误，0：系统错误
                    event.getTraceId() == null ? "" : event.getTraceId(), //调用链跟踪ID
                    event.getUserKey() == null ? "" : event.getUserKey(), //用户标识                       
                    isBiz ? (event.getBizMessage().getBizErrCode() == null ? "" : event.getBizMessage().getBizErrCode()) : (event.getErrorType() == null ? "" : event.getErrorType()),
                    isBiz ? (event.getBizMessage().getBizErrMsg() == null ? "" : event.getBizMessage().getBizErrMsg()) : ((event.getErrorMessage() == null ? "" : event.getErrorMessage()) + (event.getErrorStack() == null ? "" : event.getErrorStack()))
                ));
                isRemoteSenderOK=sender.send(msg);
            }
            
            if (isLogStoreLocal || isRemoteSenderOK==false) {   //***本地日志存储/远程发送失败***
                LOG_ERROR_MONITOR.info("{},{},{},traceId={},userKey={},errCode={},errMsg={};",
                    format_date.format(event.getTimeInMillis()), //事件时间
                    eventKey, //接口名称
                    isBiz ? "1" : "0", //1：业务错误，0：系统错误
                    event.getTraceId() == null ? "" : event.getTraceId(), //调用链跟踪ID
                    event.getUserKey() == null ? "" : event.getUserKey(), //用户标识                       
                    isBiz ? (event.getBizMessage().getBizErrCode() == null ? "" : event.getBizMessage().getBizErrCode()) : (event.getErrorType() == null ? "" : event.getErrorType()),
                    isBiz ? (event.getBizMessage().getBizErrMsg() == null ? "" : event.getBizMessage().getBizErrMsg()) : ((event.getErrorMessage() == null ? "" : event.getErrorMessage()) + (event.getErrorStack() == null ? "" : event.getErrorStack()))
                );
            }
        }
    }

    /**
     * 连续添加当前时间算起的5个统计计数器
     *
     * @param currentTimeMillis
     */
    private void addNextFiveMinuteStaticsManage(long currentTimeMillis) {
        long currentMinute = currentTimeMillis / Common.ONE_MINUTE_INTERVAL;   //当前分钟
        for (int i = 0; i < 5; i++) {
            long key = currentMinute + i;
            if (!serviceStatisticsManagers.containsKey(key)) {     //应用服务调用
                serviceStatisticsManagers.put(key, new StaticsMapManager());
            }
            if (!daoStatisticsManagers.containsKey(key)) {         //DAO接口调用
                daoStatisticsManagers.put(key, new StaticsMapManager());
            }
        }
    }
    
    /**
     * 启动消息发送服务
     */
    public synchronized void initSender(){
        //获取配置
        /**
         * HashMap<String, String> apmClientCfg =
         * ApplicationContextUtil.getBean(Common.SPRING_BEAN_KEY);
         * //判断日志存储模式是远程存储，还是本地日志存储？ if (apmClientCfg != null) { if
         * (Common.LOG_STORE_MODE_REMOTE.equals(apmClientCfg.get(Common.SPRING_KEY_CLIENT_CONFIG_LOG_STORE_MODE)))
         * { isLogStoreLocal = false; } }
         */
        LOG.info("**********************************************************************");
        LOG.info("                 Common.LogStoreMode="+Common.LogStoreMode);
        LOG.info("**********************************************************************");
        if (Common.LOG_STORE_MODE_REMOTE.equals(Common.LogStoreMode)) {
            //只有将日志存储模式设置为remote，才启动“日志发送器”
            //初始化并启动消息发送器
            LOG.info("**********************************************************************");
            LOG.info("                 LOG Will Send To Remote Server");
            LOG.info("**********************************************************************");
            if(sender==null){
                sender = TcpSocketSender.getInstance();
            }
            
            isLogStoreLocal = false;
        }else{
            isLogStoreLocal = true;
        }
    }

    private void init() {
        long start_t = System.currentTimeMillis();

        

        serviceStatisticsManagers = new ConcurrentHashMap();
        daoStatisticsManagers = new ConcurrentHashMap();
        m_errors = new AtomicInteger();
        eventQueue = new LinkedBlockingQueue<EventMessage>(SIZE);
        //启动定时线程池
        scheduledThreadPool = Executors.newScheduledThreadPool(2, new NamedThreadFactory("APM-Monitor-schedule", true));
        //启动固定大小线程池
        fixThreadPool = Executors.newFixedThreadPool(5, new NamedThreadFactory("APM-Monitor-fix", true));
        long t1 = MilliSecondTimer.currentTimeMillis();
        //初始化统计计数管理器(连续5分钟)
        addNextFiveMinuteStaticsManage(t1);
        //保证在整分钟10秒的时候启动定时线程，比如（12:21:10 , 09:09:10）,这样可以保证堆积的日志消息被充分处理完毕
        long init_delay = t1 % Common.ONE_MINUTE_INTERVAL;
        init_delay = Common.ONE_MINUTE_INTERVAL - init_delay;
        scheduledThreadPool.scheduleWithFixedDelay(new StaticsDataSender(), init_delay + 20000L, LOG_INTERVAL_DELAY, TimeUnit.MILLISECONDS);
        scheduledThreadPool.scheduleWithFixedDelay(new SystemStatusInfoCollector(), init_delay + 30000L, LOG_INTERVAL_DELAY, TimeUnit.MILLISECONDS);
        //启动消息消费者线程
        fixThreadPool.execute(new EventConsumerTask());

        

        m_active = true;
        LOG.info("**********************************************************************");
        LOG.info("    Honeycomb init success,using time:{}ms", System.currentTimeMillis() - start_t);
        LOG.info("**********************************************************************");
    }

    /**
     * 把消息压入到消息发送队列中
     *
     * @param eventMsg
     */
    public void send(EventMessage eventMsg) {
        if (!m_active) {   //判断是否初始化完成，并且可以正常接收消息了
            return;
        }
        //如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则返回false. 
        //注意，这里并没有使用阻塞,主要是为了效率考虑，如果消息队列满了，则直接把消息抛弃
        boolean result = eventQueue.offer(eventMsg);

        if (!result) {   //队列满了，塞不进去
            logQueueFullInfo(eventMsg);
        }
    }

    /**
     * 记录由于消息队列满了，导致消息被抛弃的错误信息
     *
     * @param eventMsg
     */
    private void logQueueFullInfo(EventMessage eventMsg) {
        int count = m_errors.incrementAndGet();
        /**
         * 每1000条消息堆积则打出一个错误日志
         */
        if (count % 1000 == 0 || count == 1) {
            LOG.info("Message queue is full in tcp socket sender! Count: {}" + count);
        }
        eventMsg = null;
    }

    /**
     * 构建一个消息，用于发送
     *
     * @param messageType
     * @return
     */
    public MessageTree buildMessageTree(String messageType) {
        MessageTree msg = new DefaultMessageTree();
        msg.setDomain("alc");
        msg.setIpAddress(NetworkInterfaceManager.INSTANCE.getLocalHostAddress());
        msg.setMessageType(messageType);
        msg.setTimestamp(MilliSecondTimer.currentTimeMillis());
        Thread th = Thread.currentThread();

        msg.setThreadId(Long.toString(th.getId()));
        msg.setThreadName(th.getName());

        return msg;
    }

    /**
     * 给外部提供一个发送消息的口,目前只有SystemStatusInfoCollector这个类在用
     *
     * @param msg
     */
    public boolean sendMsg(MessageTree msg) {
        return sender.send(msg);
    }

    public boolean isIsLogStoreLocal() {
        return isLogStoreLocal;
    }

}
