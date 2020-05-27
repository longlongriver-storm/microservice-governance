/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.dashboard.controller;


import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import com.storm.monitor.dashboard.config.report.ChartConfig;
import com.storm.monitor.dashboard.config.report.ReportConfig;
import com.storm.monitor.dashboard.config.report.SubReportConfig;
import com.storm.monitor.dashboard.model.ApmChartConfg;
import com.storm.monitor.dashboard.model.view.ApmChartConfgView;
import com.storm.monitor.dashboard.service.ApmChartConfgService;
import com.storm.monitor.server.model.CustomDataPickLog;
import com.storm.monitor.server.service.CustomDataPickLogService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/apmbizreport")
public class ApmBizReportController {

    @Autowired
    CustomDataPickLogService customDataPickLogService;

    @Autowired
    ApmChartConfgService apmChartConfgService;

    @RequestMapping("/bizreport")
    public String doMainDetail(String ids, HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ReportConfig> reports = new ArrayList();
        String idArray[] = ids.split(",");
        for (String id : idArray) {
            ApmChartConfg chartconfig = apmChartConfgService.queryApmChartConfgById(id);
            if (chartconfig == null) {
                continue;
            }
            ReportConfig rc = JSON.parseObject(chartconfig.getChartContent(), ReportConfig.class);
            rc.setId(chartconfig.getId());
            rc.setName(chartconfig.getChartName());
            reports.add(rc);
        }

        request.setAttribute("reports", reports);

        return "/apm/biz_report";
    }

    /**
     * 图表配置管理
     *
     * @param id
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/gotochartconfig")
    public String gotoChartConfig(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {

        return "/apm/chart_config";
    }
    
    @RequestMapping("/gotochartlist")
    public String gotoChartList(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {

        return "/apm/chart_list";
    }

    @RequestMapping({"/querychartconfig"})
    @ResponseBody
    public List<ApmChartConfg> queryCustomerError(ApmChartConfgView apmChartConfgView, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<ApmChartConfg> list = new ArrayList();
        if (apmChartConfgView != null) {
            list = this.apmChartConfgService.queryApmChartConfgByPage(apmChartConfgView);
        }

        return list;
    }

    /**
     * 打开图表配置编辑（新增）页面
     *
     * @param id
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/gotochartconfigedit")
    public String gotoChartConfigEdit(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ApmChartConfg page = new ApmChartConfg();
        if (id != null && !"".equals(id)) {
            page = apmChartConfgService.queryApmChartConfgById(id);

        }
        request.setAttribute("pj", page);

        return "/apm/add_chart_config";
    }

    @RequestMapping({"/addchartconfig"})
    @ResponseBody
    public ApmChartConfg addChartConfig(ApmChartConfg apmChartConfg, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        apmChartConfg.setModifyTime(new Date());
        ApmChartConfg oldCfg = this.apmChartConfgService.queryApmChartConfgById(apmChartConfg.getId());
        if (oldCfg == null) {   //新增
            apmChartConfg.setCreateTime(new Date());
            this.apmChartConfgService.addApmChartConfg(apmChartConfg);
        } else {              //更新
            apmChartConfg.setCreateTime(oldCfg.getCreateTime());
            this.apmChartConfgService.updateApmChartConfg(apmChartConfg);
        }

        return apmChartConfg;
    }

    /**
     * 删除配置
     *
     * @param id
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/deletechartconfig"})
    @ResponseBody
    public boolean deleteChartConfig(String id, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        int count = apmChartConfgService.deleteApmChartConfgById(id);

        if (count > 0) {
            return true;
        }
        return false;

    }

    @RequestMapping("/getdaydata")
    @ResponseBody
    public Map getDayData(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map result = new HashMap();
        ArrayList<String> legendData = new ArrayList();
        ApmChartConfg chartconfig = apmChartConfgService.queryApmChartConfgById(id);
        ReportConfig report = JSON.parseObject(chartconfig.getChartContent(), ReportConfig.class);
        SimpleDateFormat format_title = new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format_minute = new SimpleDateFormat("dd日HH时");

        long currentTime = System.currentTimeMillis();
        //当前天开始时点
        String currentDay = (new SimpleDateFormat("yyyy-MM-dd 00:00:00")).format(currentTime);
        //String startDay = currentDay - report.getIntervalTimeUnit() + 1;
        long second4currentDay = format_date.parse(currentDay).getTime();      //currentDay * 1000L * 60L * 60L * 24L;
        long second4startDay = second4currentDay - (report.getIntervalTimeUnit() - 1) * 1000L * 60L * 60L * 24L;
        int currentHour = (int) ((currentTime - second4currentDay) / (1000L * 60L * 60L));   //当前小时

        result.put("subTitle", "(" + format_title.format(second4startDay) + "---" + format_title.format(second4currentDay + 1000L * 60L * 60L * 24L) + ")");

        if (report.getSubReportConfigs() != null && report.getSubReportConfigs().size() > 0) {
            for (int idx = 0; idx < report.getSubReportConfigs().size(); idx++) {
                SubReportConfig src = report.getSubReportConfigs().get(idx);
                String sql = "SELECT \n"
                        + "    * \n"
                        + "FROM \n"
                        + "    custom_data_pick_log \n"
                        + "where \n"
                        + "    service_name='" + src.getApiName() + "'\n"
                        + "    and log_time>='" + format_date.format(second4startDay) + "'\n"
                        + "    and log_time<'" + format_date.format(second4currentDay + 1000L * 60L * 60L * 24L) + "'\n"
                        + (StringUtils.isEmpty(src.getExtendWhereSql()) ? "" : ("    and " + src.getExtendWhereSql() + "\n"))
                        + "order by\n"
                        + "    log_time asc";
                //System.out.println(sql);
                List<CustomDataPickLog> list = customDataPickLogService.queryCustomDataPickLogBySQL(sql);
                int[] minCount = new int[(report.getIntervalTimeUnit() - 1) * 24 + currentHour + 1];    //小时交易次数minCount
                int[] allCount = new int[(report.getIntervalTimeUnit() - 1) * 24 + currentHour + 1];    //总交易次数allCount
                Map<String, double[]> datas = new HashMap();
                for (ChartConfig cc : src.getChartConfigs()) {
                    datas.put("minSum_" + getSmallName(cc.getPropertyName()), new double[(report.getIntervalTimeUnit() - 1) * 24 + currentHour + 1]);
                    datas.put("allSum_" + getSmallName(cc.getPropertyName()), new double[(report.getIntervalTimeUnit() - 1) * 24 + currentHour + 1]);
                }

                //double[] minSum = new double[(report.getIntervalTimeUnit()-1)*60+currentMinute + 1];     //分钟交易金额minSum
                //double[] allSum = new double[(report.getIntervalTimeUnit()-1)*60+currentMinute + 1];     //总交易金额allSum
                for (int i = 0; i < list.size(); i++) {
                    CustomDataPickLog oneLog = list.get(i);
                    int oneMinute = (int) ((oneLog.getLogTime().getTime() - second4startDay) / (1000L * 60L * 60L));  //以开始整点天为基点的小时值偏移量
                    minCount[oneMinute] = minCount[oneMinute] + 1;
                    for (ChartConfig cc : src.getChartConfigs()) {
                        String pValue = getPropertyValue(oneLog.getPickData(), cc.getPropertyName());
                        double[] minSum = datas.get("minSum_" + getSmallName(cc.getPropertyName()));
                        minSum[oneMinute] = minSum[oneMinute] + (pValue == null ? 0 : (Double.valueOf(pValue)));
                    }

                }
                allCount[0] = minCount[0];
                for (ChartConfig cc : src.getChartConfigs()) {
                    double[] minSum = datas.get("minSum_" + getSmallName(cc.getPropertyName()));
                    double[] allSum = datas.get("allSum_" + getSmallName(cc.getPropertyName()));
                    allSum[0] = minSum[0];
                }

                if (currentHour >= 1) {
                    for (int i = 1; i < (report.getIntervalTimeUnit() - 1) * 24 + currentHour + 1; i++) {
                        allCount[i] = allCount[i - 1] + minCount[i];
                        for (ChartConfig cc : src.getChartConfigs()) {
                            double[] minSum = datas.get("minSum_" + getSmallName(cc.getPropertyName()));
                            double[] allSum = datas.get("allSum_" + getSmallName(cc.getPropertyName()));
                            allSum[i] = allSum[i - 1] + minSum[i];
                        }

                    }
                }

                String xAxis[] = new String[report.getIntervalTimeUnit() * 24];
                for (int i = 0; i < report.getIntervalTimeUnit() * 24; i++) {
                    Date one_minute = new Date(second4startDay + i * 1000L * 60L * 60L);
                    xAxis[i] = format_minute.format(one_minute);
                }

                List<HashMap> series = new ArrayList();

                if (src.isIsShowUnitCount()) {
                    legendData.add(src.getTitle() + "小时次数");
                    HashMap serie = new HashMap();
                    serie.put("name", src.getTitle() + "小时次数");
                    serie.put("type", "bar");
                    serie.put("data", minCount);
                    series.add(serie);
                }

                if (src.isIsShowAllCount()) {
                    legendData.add(src.getTitle() + "总次数");
                    HashMap serie = new HashMap();
                    serie.put("name", src.getTitle() + "总次数");
                    serie.put("type", "line");
                    serie.put("yAxisIndex", 1);
                    serie.put("data", allCount);
                    series.add(serie);
                }

                //result.put("minCount", minCount);
                //result.put("allCount", allCount);
                for (ChartConfig cc : src.getChartConfigs()) {
                    double[] minSum = datas.get("minSum_" + getSmallName(cc.getPropertyName()));
                    double[] allSum = datas.get("allSum_" + getSmallName(cc.getPropertyName()));
                    if (cc.isIsShowUnitSum()) {
                        legendData.add(cc.getUnitSumTitle());
                        HashMap serie = new HashMap();
                        serie.put("name", cc.getUnitSumTitle());
                        serie.put("type", "bar");
                        serie.put("data", datas.get("minSum_" + getSmallName(cc.getPropertyName())));
                        series.add(serie);
                        //result.put("minSum_" + getSmallName(cc.getPropertyName()), datas.get("minSum_" + getSmallName(cc.getPropertyName())));
                    }
                    if (cc.isIsShowAllSum()) {
                        legendData.add(cc.getAllSumTitle());
                        HashMap serie = new HashMap();
                        serie.put("name", cc.getAllSumTitle());
                        serie.put("type", "line");
                        serie.put("yAxisIndex", 1);
                        serie.put("data", datas.get("allSum_" + getSmallName(cc.getPropertyName())));
                        series.add(serie);
                        //result.put("allSum_" + getSmallName(cc.getPropertyName()), datas.get("allSum_" + getSmallName(cc.getPropertyName())));
                    }

                }

                result.put("legendData", legendData);
                result.put("xAxis", xAxis);
                result.put("series", series);
            }

        }

        result.put("yAxisFirstName", report.getyAxisFirstName());
        result.put("yAxisFirstUnit", report.getyAxisFirstUnit());
        result.put("yAxisSecondName", report.getyAxisSecondName());
        result.put("yAxisSecondUnit", report.getyAxisSecondUnit());

        return result;
    }

    @RequestMapping("/gethourdata")
    @ResponseBody
    public Map getHourData(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map result = new HashMap();
        ArrayList<String> legendData = new ArrayList();
        ApmChartConfg chartconfig = apmChartConfgService.queryApmChartConfgById(id);
        ReportConfig report = JSON.parseObject(chartconfig.getChartContent(), ReportConfig.class);

        SimpleDateFormat format_title = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format_minute = new SimpleDateFormat("HH:mm");

        long currentTime = System.currentTimeMillis();
        //当前小时开始时点
        long currentHour = currentTime / (1000L * 60L * 60L);
        long startHour = currentHour - report.getIntervalTimeUnit() + 1;
        long second4currentHour = currentHour * 1000L * 60L * 60L;
        long second4startHour = startHour * 1000L * 60L * 60L;
        int currentMinute = (int) ((currentTime - second4currentHour) / (1000L * 60L));   //当前分钟

        result.put("subTitle", "(" + format_title.format(second4startHour) + "---" + format_title.format(second4currentHour + 1000L * 60L * 60L) + ")");

        if (report.getSubReportConfigs() != null && report.getSubReportConfigs().size() > 0) {
            for (int idx = 0; idx < report.getSubReportConfigs().size(); idx++) {
                SubReportConfig src = report.getSubReportConfigs().get(idx);
                String sql = "SELECT \n"
                        + "    * \n"
                        + "FROM \n"
                        + "    custom_data_pick_log \n"
                        + "where \n"
                        + "    service_name='" + src.getApiName() + "'\n"
                        + "    and log_time>='" + format_date.format(second4startHour) + "'\n"
                        + "    and log_time<'" + format_date.format(second4currentHour + 1000L * 60L * 60L) + "'\n"
                        + (StringUtils.isEmpty(src.getExtendWhereSql()) ? "" : ("    and " + src.getExtendWhereSql() + "\n"))
                        + "order by\n"
                        + "    log_time asc";
                //System.out.println(sql);
                List<CustomDataPickLog> list = customDataPickLogService.queryCustomDataPickLogBySQL(sql);
                int[] minCount = new int[(report.getIntervalTimeUnit() - 1) * 60 + currentMinute + 1];    //分钟交易次数minCount
                int[] allCount = new int[(report.getIntervalTimeUnit() - 1) * 60 + currentMinute + 1];    //总交易次数allCount
                Map<String, double[]> datas = new HashMap();
                for (ChartConfig cc : src.getChartConfigs()) {
                    datas.put("minSum_" + getSmallName(cc.getPropertyName()), new double[(report.getIntervalTimeUnit() - 1) * 60 + currentMinute + 1]);
                    datas.put("allSum_" + getSmallName(cc.getPropertyName()), new double[(report.getIntervalTimeUnit() - 1) * 60 + currentMinute + 1]);
                }

                //double[] minSum = new double[(report.getIntervalTimeUnit()-1)*60+currentMinute + 1];     //分钟交易金额minSum
                //double[] allSum = new double[(report.getIntervalTimeUnit()-1)*60+currentMinute + 1];     //总交易金额allSum
                for (int i = 0; i < list.size(); i++) {
                    CustomDataPickLog oneLog = list.get(i);
                    int oneMinute = (int) ((oneLog.getLogTime().getTime() - second4startHour) / (1000L * 60L));  //以开始整点小时为基点的分钟值偏移量
                    minCount[oneMinute] = minCount[oneMinute] + 1;
                    for (ChartConfig cc : src.getChartConfigs()) {
                        String pValue = getPropertyValue(oneLog.getPickData(), cc.getPropertyName());
                        double[] minSum = datas.get("minSum_" + getSmallName(cc.getPropertyName()));
                        minSum[oneMinute] = minSum[oneMinute] + (pValue == null ? 0 : (Double.valueOf(pValue)));
                    }

                }
                allCount[0] = minCount[0];
                for (ChartConfig cc : src.getChartConfigs()) {
                    double[] minSum = datas.get("minSum_" + getSmallName(cc.getPropertyName()));
                    double[] allSum = datas.get("allSum_" + getSmallName(cc.getPropertyName()));
                    allSum[0] = minSum[0];
                }

                if (currentMinute >= 1) {
                    for (int i = 1; i < (report.getIntervalTimeUnit() - 1) * 60 + currentMinute + 1; i++) {
                        allCount[i] = allCount[i - 1] + minCount[i];
                        for (ChartConfig cc : src.getChartConfigs()) {
                            double[] minSum = datas.get("minSum_" + getSmallName(cc.getPropertyName()));
                            double[] allSum = datas.get("allSum_" + getSmallName(cc.getPropertyName()));
                            allSum[i] = allSum[i - 1] + minSum[i];
                        }

                    }
                }

                String xAxis[] = new String[report.getIntervalTimeUnit() * 60];
                for (int i = 0; i < report.getIntervalTimeUnit() * 60; i++) {
                    Date one_minute = new Date(second4startHour + i * 1000L * 60L);
                    xAxis[i] = format_minute.format(one_minute);
                }

                List<HashMap> series = new ArrayList();

                if (src.isIsShowUnitCount()) {
                    legendData.add(src.getTitle() + "分钟次数");
                    HashMap serie = new HashMap();
                    serie.put("name", src.getTitle() + "分钟次数");
                    serie.put("type", "bar");
                    serie.put("data", minCount);
                    series.add(serie);
                }

                if (src.isIsShowAllCount()) {
                    legendData.add(src.getTitle() + "总次数");
                    HashMap serie = new HashMap();
                    serie.put("name", src.getTitle() + "总次数");
                    serie.put("type", "line");
                    serie.put("yAxisIndex", 1);
                    serie.put("data", allCount);
                    series.add(serie);
                }

                //result.put("minCount", minCount);
                //result.put("allCount", allCount);
                for (ChartConfig cc : src.getChartConfigs()) {
                    double[] minSum = datas.get("minSum_" + getSmallName(cc.getPropertyName()));
                    double[] allSum = datas.get("allSum_" + getSmallName(cc.getPropertyName()));
                    if (cc.isIsShowUnitSum()) {
                        legendData.add(cc.getUnitSumTitle());
                        HashMap serie = new HashMap();
                        serie.put("name", cc.getUnitSumTitle());
                        serie.put("type", "bar");
                        serie.put("data", datas.get("minSum_" + getSmallName(cc.getPropertyName())));
                        series.add(serie);
                        //result.put("minSum_" + getSmallName(cc.getPropertyName()), datas.get("minSum_" + getSmallName(cc.getPropertyName())));
                    }
                    if (cc.isIsShowAllSum()) {
                        legendData.add(cc.getAllSumTitle());
                        HashMap serie = new HashMap();
                        serie.put("name", cc.getAllSumTitle());
                        serie.put("type", "line");
                        serie.put("yAxisIndex", 1);
                        serie.put("data", datas.get("allSum_" + getSmallName(cc.getPropertyName())));
                        series.add(serie);
                        //result.put("allSum_" + getSmallName(cc.getPropertyName()), datas.get("allSum_" + getSmallName(cc.getPropertyName())));
                    }

                }

                result.put("legendData", legendData);
                result.put("xAxis", xAxis);
                result.put("series", series);
            }

        }

        result.put("yAxisFirstName", report.getyAxisFirstName());
        result.put("yAxisFirstUnit", report.getyAxisFirstUnit());
        result.put("yAxisSecondName", report.getyAxisSecondName());
        result.put("yAxisSecondUnit", report.getyAxisSecondUnit());

        return result;
    }

    @RequestMapping("/getsplasheddata")
    @ResponseBody
    public Map getSplashedData(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map result = new HashMap();
        ArrayList<String> legendData = new ArrayList();
        ApmChartConfg chartconfig = apmChartConfgService.queryApmChartConfgById(id);
        ReportConfig report = JSON.parseObject(chartconfig.getChartContent(), ReportConfig.class);

        SimpleDateFormat format_title = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format_minute = new SimpleDateFormat("HH:mm");

        long currentTime = System.currentTimeMillis();
        //当前小时开始时点
        long currentHour = currentTime / (1000L * 60L * 60L);
        long startHour = currentHour - report.getIntervalTimeUnit() + 1;
        long second4currentHour = currentHour * 1000L * 60L * 60L;
        long second4startHour = startHour * 1000L * 60L * 60L;
        int currentMinute = (int) ((currentTime - second4currentHour) / (1000L * 60L));   //当前分钟

        result.put("subTitle", "(" + format_title.format(second4startHour) + "---" + format_title.format(second4currentHour + 1000L * 60L * 60L) + ")");

        List<HashMap> series = new ArrayList();

        if (report.getSubReportConfigs() != null && report.getSubReportConfigs().size() > 0) {
            for (int idx = 0; idx < report.getSubReportConfigs().size(); idx++) {
                SubReportConfig src = report.getSubReportConfigs().get(idx);
                legendData.add(src.getTitle());
                String sql = "SELECT \n"
                        + "    * \n"
                        + "FROM \n"
                        + "    custom_data_pick_log \n"
                        + "where \n"
                        + "    service_name='" + src.getApiName() + "'\n"
                        + "    and log_time>='" + format_date.format(second4startHour) + "'\n"
                        + "    and log_time<'" + format_date.format(second4currentHour + 1000L * 60L * 60L) + "'\n"
                        + (StringUtils.isEmpty(src.getExtendWhereSql()) ? "" : ("    and " + src.getExtendWhereSql() + "\n"))
                        + "order by\n"
                        + "    log_time asc";
                //System.out.println(sql);
                List<CustomDataPickLog> list = customDataPickLogService.queryCustomDataPickLogBySQL(sql);
                List<Object[]> datas = new ArrayList();

                for (int i = 0; i < list.size(); i++) {
                    CustomDataPickLog oneLog = list.get(i);
                    long thisMinute4One = oneLog.getLogTime().getTime() / (1000L * 60L);
                    thisMinute4One = thisMinute4One * 1000L * 60L;
                    Date one_minute = new Date(thisMinute4One);
                    Object[] oneDot = new Object[]{format_minute.format(one_minute), 0, ""};
                    for (ChartConfig cc : src.getChartConfigs()) {
                        String pValue = getPropertyValue(oneLog.getPickData(), cc.getPropertyName());
                        if ("".equals(oneDot[2].toString())) {
                            oneDot[1] = (pValue == null ? 0 : (Double.valueOf(pValue)));       //Y轴
                            oneDot[2] = cc.getAllSumTitle() + ":" + pValue + cc.getUnitSumTitle();     //显示文字
                        } else {
                            oneDot[2] = oneDot[2].toString() + "<br>" + cc.getAllSumTitle() + ":" + pValue + cc.getUnitSumTitle();   //显示多个属性信息
                        }
                    }
                    oneDot[2] = "用户标识:"+oneLog.getUserKey()+"<br>"+oneDot[2].toString();
                    datas.add(oneDot);   //加入列表
                }

                HashMap serie = new HashMap();
                serie.put("name", src.getTitle());
                serie.put("type", "scatter");
                serie.put("data", datas);
                series.add(serie);

            }

            String xAxis[] = new String[report.getIntervalTimeUnit() * 60];
            for (int i = 0; i < report.getIntervalTimeUnit() * 60; i++) {
                Date one_minute = new Date(second4startHour + i * 1000L * 60L);
                xAxis[i] = format_minute.format(one_minute);   //坐标是整分钟值
            }

            result.put("legendData", legendData);
            result.put("xAxis", xAxis);
            result.put("series", series);

        }

        result.put("yAxisFirstName", report.getyAxisFirstName());
        result.put("yAxisFirstUnit", report.getyAxisFirstUnit());
        result.put("yAxisSecondName", report.getyAxisSecondName());
        result.put("yAxisSecondUnit", report.getyAxisSecondUnit());

        return result;
    }

    private String getSmallName(String name) {
        return name.replaceAll("\\.", "_");
    }

    private String getPropertyValue(String str, String propertyName) {
        if (str == null) {
            return null;
        }
        String result = null;
        int idx = str.indexOf(propertyName + "=");
        if (idx < 0) {
            return null;
        }
        result = str.substring(idx + propertyName.length() + 1);
        idx = result.indexOf(";;;");
        if (idx < 0) {
            return result;
        }
        result = result.substring(0, idx);

        return result;
    }

}
