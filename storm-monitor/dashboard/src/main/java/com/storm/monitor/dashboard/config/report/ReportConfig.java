/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.dashboard.config.report;

import java.util.List;

/**
 * APM监控图表配置
 * @author lixin
 */
public class ReportConfig {
    /**
     * 报表ID，也是报表的唯一标识
     */
    String id;
    /**
     * 报表名称
     */
    String name;
    /**
     * 报表类型,目前共有4种（day_static：天静态报表(小时单位刻度)；hour_static：小时静态报表（分钟单位刻度）；hour_dynamic：小时动态报表（分钟刻度）；hour_splashed：小时动态散点报表（分钟刻度））
     */
    String type;
    /**
     * 报表主标题
     */
    String mainTitle;
    /**
     * 报表子标题
     */
    String subTitle;
    /**
     * 第一条纵轴的名称
     */
    String yAxisFirstName="调用次数";
    /**
     * 第一条纵轴的单位名称，如：次，个...
     */
    String yAxisFirstUnit="次";
    /**
     * 第二条纵轴的名称
     */
    String yAxisSecondName;
    /**
     * 第二条纵轴的单位名称，如：元，个...
     */
    String yAxisSecondUnit;
    
    /**
     * 间隔时间单位(小时/天）（从当前时间前推，分钟时间还是会按整点小时来算）
     */
    int intervalTimeUnit;
    
    List<SubReportConfig> subReportConfigs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getyAxisFirstName() {
        return yAxisFirstName;
    }

    public void setyAxisFirstName(String yAxisFirstName) {
        this.yAxisFirstName = yAxisFirstName;
    }

    public String getyAxisFirstUnit() {
        return yAxisFirstUnit;
    }

    public void setyAxisFirstUnit(String yAxisFirstUnit) {
        this.yAxisFirstUnit = yAxisFirstUnit;
    }

    public String getyAxisSecondName() {
        return yAxisSecondName;
    }

    public void setyAxisSecondName(String yAxisSecondName) {
        this.yAxisSecondName = yAxisSecondName;
    }

    public String getyAxisSecondUnit() {
        return yAxisSecondUnit;
    }

    public void setyAxisSecondUnit(String yAxisSecondUnit) {
        this.yAxisSecondUnit = yAxisSecondUnit;
    }

    public int getIntervalTimeUnit() {
        return intervalTimeUnit;
    }

    public void setIntervalTimeUnit(int intervalTimeUnit) {
        this.intervalTimeUnit = intervalTimeUnit;
    }


    public List<SubReportConfig> getSubReportConfigs() {
        return subReportConfigs;
    }

    public void setSubReportConfigs(List<SubReportConfig> subReportConfigs) {
        this.subReportConfigs = subReportConfigs;
    }
    
    
    
}
