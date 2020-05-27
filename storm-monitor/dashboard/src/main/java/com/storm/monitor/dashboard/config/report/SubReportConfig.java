/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.dashboard.config.report;

import java.util.List;

/**
 * 子报表配置
 *
 * @author lixin
 */
public class SubReportConfig {

    /**
     * 接口名称
     */
    String apiName;
    /**
     * 子报表标题（主要用于展示【次数】统计）
     */
    String title;
    /**
     * 扩展的查询条件SQL，如果不为空，会以and的形式被添加到SQL的where条件中 注意：系统自动生成部分只有日期过滤条件
     */
    String extendWhereSql;
    /**
     * 单位时间次数合计（对天报表，就是小时合计；对小时报表，就是分钟合计）:柱状图
     */
    boolean isShowUnitCount;
    /**
     * 截止到当前单位时间的次数总计:折线图
     */
    boolean isShowAllCount;
    
    /**
     * 具体业务图表配置
     */
    List<ChartConfig> chartConfigs;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtendWhereSql() {
        return extendWhereSql;
    }

    public void setExtendWhereSql(String extendWhereSql) {
        this.extendWhereSql = extendWhereSql;
    }

    public boolean isIsShowUnitCount() {
        return isShowUnitCount;
    }

    public void setIsShowUnitCount(boolean isShowUnitCount) {
        this.isShowUnitCount = isShowUnitCount;
    }

    public boolean isIsShowAllCount() {
        return isShowAllCount;
    }

    public void setIsShowAllCount(boolean isShowAllCount) {
        this.isShowAllCount = isShowAllCount;
    }

    public List<ChartConfig> getChartConfigs() {
        return chartConfigs;
    }

    public void setChartConfigs(List<ChartConfig> chartConfigs) {
        this.chartConfigs = chartConfigs;
    }

    
}
