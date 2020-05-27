/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.dashboard.config.report;

/**
 * 报表上某一属性的图表展示配置
 * @author lixin
 */
public class ChartConfig {
    /**
     * 指定的属性名称
     */
    String propertyName;
    
    /**
     * 单位时间数量合计（小时合计；分钟合计）
     */
    boolean isShowUnitSum;
    /**
     * 属性的单位时间数量合计显示标题
     */
    String unitSumTitle;
    /**
     * 截止到当前单位时间的数量总计
     */
    boolean isShowAllSum;
    /**
     * 截止到当前单位时间的数量的显示标题
     */
    String allSumTitle;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public boolean isIsShowUnitSum() {
        return isShowUnitSum;
    }

    public void setIsShowUnitSum(boolean isShowUnitSum) {
        this.isShowUnitSum = isShowUnitSum;
    }

    public String getUnitSumTitle() {
        return unitSumTitle;
    }

    public void setUnitSumTitle(String unitSumTitle) {
        this.unitSumTitle = unitSumTitle;
    }

    public boolean isIsShowAllSum() {
        return isShowAllSum;
    }

    public void setIsShowAllSum(boolean isShowAllSum) {
        this.isShowAllSum = isShowAllSum;
    }

    public String getAllSumTitle() {
        return allSumTitle;
    }

    public void setAllSumTitle(String allSumTitle) {
        this.allSumTitle = allSumTitle;
    }
    
    
}
