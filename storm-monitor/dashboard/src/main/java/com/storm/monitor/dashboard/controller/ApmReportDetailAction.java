/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.dashboard.controller;

import com.alibaba.fastjson.JSON;

import com.storm.monitor.server.model.ErrorLog;
import com.storm.monitor.server.model.MemoryGcMonitorLog;
import com.storm.monitor.server.model.ServiceMonitorLog;
import com.storm.monitor.server.model.SystemMonitorLog;
import com.storm.monitor.server.model.view.ErrorLogView;
import com.storm.monitor.server.service.DaoMonitorLogDayService;
import com.storm.monitor.server.service.DaoMonitorLogHourService;
import com.storm.monitor.server.service.DaoMonitorLogService;
import com.storm.monitor.server.service.ErrorLogService;
import com.storm.monitor.server.service.MemoryGcMonitorLogService;
import com.storm.monitor.server.service.ServiceMonitorLogDayService;
import com.storm.monitor.server.service.ServiceMonitorLogHourService;
import com.storm.monitor.server.service.ServiceMonitorLogService;
import com.storm.monitor.server.service.SystemMonitorLogService;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/apmreportdetail")
public class ApmReportDetailAction {

    public static final String DATE_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

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
    SystemMonitorLogService systemMonitorLogService;

    @Autowired
    MemoryGcMonitorLogService memoryGcMonitorLogService;

    @Autowired
    ErrorLogService errorLogService;

    @RequestMapping("/servicedetail")
    public String doServiceDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/apm/service_detail";
    }

    @RequestMapping("/daodetail")
    public String doDaoDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/apm/dao_detail";
    }

    @RequestMapping("/systemdetail")
    public String doSystemDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/apm/system_detail";
    }

    /**
     * 获取最近分钟(小时、天)的业务错误TOPN
     *
     * @param idao 是服务接口
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getnewestminutebizerror")
    @ResponseBody
    public List<ServiceMonitorLog> getNewestMinuteBizError(String idao, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tableName = "service_monitor_log_hour";
        if (idao != null && idao.length() > 0) {
            tableName = idao;

        }
        //查询错误量最多的最新5个接口
        String sql = "    SELECT \n"
                + "        a.*,\n"
                + "        (select c.lastest_biz_error_msg from service_monitor_log c where c.service_name=a.service_name and c.log_time>date_add(now(), interval -1 hour) and c.biz_failure_count>0 order by c.log_time desc limit 1) as lastest_biz_error_msg\n"
                + "    FROM\n"
                + "        (SELECT \n"
                + "            service_name,\n"
                + "            SUM(success_count) AS success_count,\n"
                + "            SUM(biz_failure_count) AS biz_failure_count\n"
                + "        FROM\n"
                + "            %s \n"
                + "        WHERE\n"
                + "            log_time = (SELECT log_time FROM %s ORDER BY log_time DESC LIMIT 1)\n"
                + "        GROUP BY service_name\n"
                + "        ORDER BY SUM(biz_failure_count) DESC\n"
                + "        LIMIT 20) a\n"
                + "    WHERE \n"
                + "        a.biz_failure_count>0";
        sql = String.format(sql, tableName, tableName);
        List<ServiceMonitorLog> list = serviceMonitorLogService.queryServiceMonitorLogBySQL(sql);
        return list;
    }

    /**
     * 获取最近分钟(小时、天)的错误TOPN
     *
     * @param idao 是服务接口
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getnewestminuteerror")
    @ResponseBody
    public List<ServiceMonitorLog> getNewestMinuteError(String idao, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tableName = "service_monitor_log_hour";
        if (idao != null && idao.length() > 0) {
            tableName = idao;

        }
        //查询错误量最多的最新5个接口
        String sql = "select a.* from (\n"
                + "     SELECT \n"
                + "		   service_name,\n"
                + "		   sum(success_count) as success_count,\n"
                + "        sum(failure_count) as failure_count  \n"
                + "     FROM \n"
                + "		   %s \n"
                + "	    where \n"
                + "		   log_time=(select log_time from %s order by log_time desc limit 1) \n"
                + "     group by "
                + "        service_name\n"
                + ") a \n"
                + "where "
                + "    a.failure_count>0 \n"
                + "order by \n"
                + "	   a.failure_count desc \n";
        sql = String.format(sql, tableName, tableName);
        List<ServiceMonitorLog> list = serviceMonitorLogService.queryServiceMonitorLogBySQL(sql);
        return list;
    }

    /**
     * 获取最近分钟(小时、天)的性能最差TOP5
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getnewestminuteperformance")
    @ResponseBody
    public List<ServiceMonitorLog> getNewestMinutePerformance(String idao, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tableName = "service_monitor_log_hour";
        if (idao != null && idao.length() > 0) {
            tableName = idao;
        }
        //查询错误量最多的最新5个接口
        String sql = "select a.* from (\n"
                + "	SELECT \n"
                + "		service_name,\n"
                + "		sum(success_count+failure_count) as success_count,\n"
                + "		max(max_elapsed) as max_elapsed,\n"
                + "     sum(success_count*avg_elapsed)/sum(success_count) as avg_elapsed\n"
                + "	FROM \n"
                + "		%s \n"
                + "	where \n"
                + "		log_time=(select log_time from %s order by log_time desc limit 1) \n"
                + "	group by service_name\n"
                + ") \n"
                + "a order by a.avg_elapsed desc limit 20";
        sql = String.format(sql, tableName, tableName);
        List<ServiceMonitorLog> list = serviceMonitorLogService.queryServiceMonitorLogBySQL(sql);
        return list;
    }

    /**
     * 获取最近分钟的调用服务最多TOP5
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getnewestminutecallmax")
    @ResponseBody
    public List<ServiceMonitorLog> getNewestMinuteCallMax(String idao, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tableName = "service_monitor_log_hour";
        if (idao != null && idao.length() > 0) {
            tableName = idao;
        }
        //查询错误量最多的最新5个接口
        String sql = "select a.service_name,a.success_count,a.failure_count from (\n"
                + "	SELECT \n"
                + "		service_name,\n"
                + "		sum(success_count) as success_count,\n"
                + "     sum(failure_count) as failure_count,\n"
                + "     sum(success_count+failure_count) as total_count\n"
                + "	FROM \n"
                + "		%s \n"
                + "	where \n"
                + "		log_time=(select log_time from %s order by log_time desc limit 1) \n"
                + "	group by service_name\n"
                + ") \n"
                + "a order by a.total_count desc limit 20";
        sql = String.format(sql, tableName, tableName);
        List<ServiceMonitorLog> list = serviceMonitorLogService.queryServiceMonitorLogBySQL(sql);
        return list;
    }

    /**
     * 获取最近分钟的总服务资源占用最多TOP5
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getnewestminuteloadmax")
    @ResponseBody
    public List<ServiceMonitorLog> getNewestMinuteLoadMax(String idao, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tableName = "service_monitor_log_hour";
        if (idao != null && idao.length() > 0) {
            tableName = idao;
        }
        //查询错误量最多的最新5个接口
        String sql = "select a.service_name,a.success_count,a.failure_count,a.avg_elapsed,a.total_time from (\n"
                + " SELECT \n"
                + "		service_name,\n"
                + "		sum(success_count) as success_count,\n"
                + "     sum(failure_count) as failure_count,\n"
                + "     sum(success_count*avg_elapsed)/sum(success_count) as avg_elapsed,\n"
                + "     sum(success_count*avg_elapsed) as total_time\n"
                + " FROM \n"
                + "		%s \n"
                + " where \n"
                + "		log_time=(select log_time from %s order by log_time desc limit 1) \n"
                + " group by service_name\n"
                + ") \n"
                + "a order by a.total_time desc limit 5";
        sql = String.format(sql, tableName, tableName);
        List<ServiceMonitorLog> list = serviceMonitorLogService.queryServiceMonitorLogBySQL(sql);
        return list;
    }

    /**
     * 总调用量（成功+失败）变化最厉害的接口
     *
     * @param idao
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getnewesthourcallchangemax")
    @ResponseBody
    public List<ServiceMonitorLog> getNewestHourCallChangeMax(String idao, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String tableName = "service_monitor_log_hour";
        if (idao != null && idao.length() > 0) {
            tableName = idao;
        }

        /**
         * 调用量增长最多 success_count:当前调用量 failure_count:之前调用量 max_elapsed:变化率
         * (优化版本)
         */
        String sql = "select d.service_name,d.success_count,ifnull(d.old_success_count,0) as failure_count,d.incremt_ration as max_elapsed from (\n"
                + "	select c.service_name,c.success_count,c.old_success_count,abs(success_count-ifnull(c.old_success_count,0))/ifnull(c.old_success_count,1) as incremt_ration  from (\n"
                + "		select a.*,b.success_count as old_success_count from\n"
                + "			(\n"
                + "				 SELECT \n"
                + "						service_name,\n"
                + "						sum(success_count+failure_count) as success_count\n"
                + "				 FROM \n"
                + "						%s\n"
                + "				 where \n"
                + "						log_time=(select log_time from %s order by log_time desc limit 1) \n"
                + "				 group by service_name\n"
                + "			) a\n"
                + "		left join \n"
                + "			(\n"
                + "				\n"
                + "				 SELECT \n"
                + "						service_name,\n"
                + "						sum(success_count+failure_count) as success_count\n"
                + "				 FROM \n"
                + "						%s\n"
                + "				 where \n"
                + "						log_time=(select log_time from %s where log_time<(\n"
                + "					select log_time from %s order by log_time desc limit 1\n"
                + "				) order by log_time desc limit 1) \n"
                + "				 group by service_name\n"
                + "\n"
                + "			) b\n"
                + "		on\n"
                + "			a.service_name=b.service_name\n"
                + "	) c\n"
                + ") d order by d.incremt_ration desc limit 5";
        sql = String.format(sql, tableName, tableName, tableName, tableName, tableName);
        List<ServiceMonitorLog> list = serviceMonitorLogService.queryServiceMonitorLogBySQL(sql);
        return list;
    }

    /**
     * 大后台系统性能
     *
     * @param idao
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getnewestminutesystemstatusavg")
    @ResponseBody
    public Map getNewestMinuteSystemStatusAvg(String idao, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sql = "SELECT \n"
                + "		log_time,\n"
                + "        sum(disk_total) as disk_total,\n"
                + "        sum(disk_free) as disk_free,\n"
                + "        sum(disk_usable) as disk_usable,\n"
                + "        avg(os_systemLoadAverage) as os_systemLoadAverage,\n"
                + "        sum(memory_max) as memory_max,\n"
                + "        sum(memory_total) as memory_total,\n"
                + "        sum(memory_free) as memory_free,\n"
                + "        sum(memory_heapUsage) as memory_heapUsage,\n"
                + "        sum(memory_nonHeapUsage) as memory_nonHeapUsage,\n"
                + "        sum(os_totalPhysicalMemory) as os_totalPhysicalMemory,\n"
                + "        sum(os_freePhysicalMemory) as os_freePhysicalMemory\n"
                + " FROM \n"
                + "		system_monitor_log\n"
                + " where \n"
                + "		log_time=(select log_time from system_monitor_log order by log_time desc limit 1)";
        List<SystemMonitorLog> list = systemMonitorLogService.querySystemMonitorLogBySQL(sql);
        SystemMonitorLog result = list.get(0);
        Map rs = BeanUtils.describe(result);
        return rs;
    }

    /**
     * 最近一小时系统性能变化数据，按时间顺序排序
     *
     * @param idao
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getnewesthoursystemstatus")
    @ResponseBody
    public List<SystemMonitorLog> getNewestHourSystemStatus(String idao, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sql = " SELECT \n"
                + "		log_time,\n"
                + "        sum(disk_total) as disk_total,\n"
                + "        sum(disk_free) as disk_free,\n"
                + "        sum(disk_usable) as disk_usable,\n"
                + "        avg(os_systemLoadAverage) as os_systemLoadAverage,\n"
                + "        avg(memory_max) as memory_max,\n"
                + "        avg(memory_total) as memory_total,\n"
                + "        avg(memory_free) as memory_free,\n"
                + "        avg(memory_heapUsage) as memory_heapUsage,\n"
                + "        avg(memory_nonHeapUsage) as memory_nonHeapUsage,\n"
                + "        avg(os_totalPhysicalMemory) as os_totalPhysicalMemory,\n"
                + "        avg(os_freePhysicalMemory) as os_freePhysicalMemory\n"
                + " FROM \n"
                + "		system_monitor_log\n"
                + " where \n"
                + "		log_time>date_add((select log_time from system_monitor_log order by log_time desc limit 1) , interval -1 hour)\n"
                + "group by\n"
                + "        log_time\n"
                + "order by\n"
                + "		log_time";
        //os_systemLoadAverage
        List<SystemMonitorLog> list = systemMonitorLogService.querySystemMonitorLogBySQL(sql);
        return list;
    }

    /**
     * 最近一分钟系统性能数据，单台主机
     *
     * @param idao
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getnewestminutesystemstatus")
    @ResponseBody
    public List<SystemMonitorLog> getNewestMinuteSystemStatus(String idao, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sql = " select \n"
                + "	* \n"
                + "from \n"
                + "	system_monitor_log a \n"
                + "where \n"
                + "	a.log_time=(select log_time from system_monitor_log order by log_time desc limit 1)";
        //os_systemLoadAverage
        List<SystemMonitorLog> list = systemMonitorLogService.querySystemMonitorLogBySQL(sql);
        return list;
    }

    /**
     * 最近一小时系统性能负载数据，单台主机
     *
     * @param idao
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/getnewesthoursystemLoad")
    @ResponseBody
    public List<SystemMonitorLog> getNewestHourSystemLoad(String idao, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sql = "select \n"
                + "    a.log_time,\n"
                + "    a.machine_address,\n"
                + "    a.os_systemLoadAverage,\n"
                + "    a.memory_total,\n"
                + "    a.memory_free\n"
                + "from \n"
                + "    system_monitor_log a \n"
                + "where \n"
                + "    a.log_time>date_add((select log_time from system_monitor_log order by log_time desc limit 1) , interval -1 hour)\n"
                + "order by "
                + "    a.machine_address,a.log_time asc";
        //os_systemLoadAverage
        List<SystemMonitorLog> list = systemMonitorLogService.querySystemMonitorLogBySQL(sql);
        return list;
    }

    @RequestMapping("/getnewestgcinfo")
    @ResponseBody
    public List<MemoryGcMonitorLog> getNewestGcInfo(String idao, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String sql = "select \n"
                + "	  *\n"
                + "from \n"
                + "	  memory_gc_monitor_log\n"
                + "where \n"
                + "	  log_time=(select log_time from memory_gc_monitor_log order by log_time desc limit 1)\n"
                + "order by\n"
                + "	  machine_address,gc_name";
        //os_systemLoadAverage
        List<MemoryGcMonitorLog> list = memoryGcMonitorLogService.queryMemoryGcMonitorLogBySQL(sql);
        return list;
    }

    @RequestMapping({"/getNewestDangerousUsers"})
    @ResponseBody
    public List<Map<String, Object>> getNewestDangerousUsers(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String sql = "select \n"
                + "a.*,\n"
                + "    (select b.log_time from error_log b where b.user_key=a.user_key order by log_time desc limit 1) as lastest_log_time \n"
                + "from (\n"
                + "SELECT \n"
                + "        count(1) as user_error_count,\n"
                + "        user_key,\n"
                + "        REVERSE(user_key) as user_key_reverse,\n"
                + "        error_code,\n"
                + "        error_msg\n"
                + "FROM \n"
                + "        error_log \n"
                + "where \n"
                + "        log_time>date_add(now() , interval -1 day)\n"
                + "group by user_key\n"
                + "    ) a  where a.user_key_reverse!=''\n"
                + "order by \n"
                + "   a.user_error_count desc \n"
                + "limit 300";

        List<Map<String, Object>> list = this.errorLogService.queryErrorLog2MapBySQL(sql);
        return list;
    }

    @RequestMapping({"/executeSelectSQL"})
    @ResponseBody
    public List<Map<String, Object>> executeSelectSQL(String sql, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (!sql.startsWith("select")) {
            return null;
        }
        List<Map<String, Object>> list = this.errorLogService.queryErrorLog2MapBySQL(sql);
        return list;
    }

    @RequestMapping({"/querycustomererror"})
    @ResponseBody
    public List<ErrorLog> queryCustomerError(ErrorLogView errorLogView, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("----------------------------------------------------");
        System.out.println(JSON.toJSONString(errorLogView));
        System.out.println("----------------------------------------------------");
        if ((errorLogView.getErrorMsg() != null) && (errorLogView.getErrorMsg().length() > 0)) {
            errorLogView.setErrorMsg("%" + errorLogView.getErrorMsg() + "%");
        }
        List<ErrorLog> list = this.errorLogService.queryErrorLogByPage(errorLogView);

        return list;
    }

}
