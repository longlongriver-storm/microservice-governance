package com.storm.monitor.dashboard.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 【图表配置】的PoJo类，也就是数据库表apm_chart_confg的映射类
 * Copyriht: Copyright (c) 2020
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2018-10-19 09:45
 *
 */
public class ApmChartConfg implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    private java.lang.String id;
    /**
     * 图表名称
     */
    private java.lang.String chartName;
    /**
     * 图表说明
     */
    private java.lang.String description;
    /**
     * 图表内容
     */
    private java.lang.String chartContent;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 修改时间
     */
    private java.util.Date modifyTime;

    public java.lang.String getId() {
        return id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    public java.lang.String getChartName() {
        return chartName;
    }

    public void setChartName(java.lang.String chartName) {
        this.chartName = chartName;
    }

    public java.lang.String getDescription() {
        return description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getChartContent() {
        return chartContent;
    }

    public void setChartContent(java.lang.String chartContent) {
        this.chartContent = chartContent;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    public java.util.Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(java.util.Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 复制所有属性到指定对象
     *
     * @param targetApmChartConfg 属性赋值目标对象
     */
    public void copyPropertiesTo(ApmChartConfg targetApmChartConfg) {
        if (targetApmChartConfg == null) {
            return;
        }
        targetApmChartConfg.setId(this.getId());
        targetApmChartConfg.setChartName(this.getChartName());
        targetApmChartConfg.setDescription(this.getDescription());
        targetApmChartConfg.setChartContent(this.getChartContent());
        targetApmChartConfg.setCreateTime(this.getCreateTime());
        targetApmChartConfg.setModifyTime(this.getModifyTime());
    }

    public boolean equals(Object object) {
        if (!(object instanceof ApmChartConfg)) {
            return false;
        }
        ApmChartConfg apmChartConfg2 = (ApmChartConfg) object;
        return new EqualsBuilder()
            .appendSuper(super.equals(object))
            .append(this.id, apmChartConfg2.id)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(-528253723, -475504089)
            .appendSuper(super.hashCode())
            .append(this.id)
            .toHashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("id=").append(this.getId()).append(",");
        sb.append("chartName=").append(this.getChartName()).append(",");
        sb.append("description=").append(this.getDescription()).append(",");
        sb.append("chartContent=").append(this.getChartContent()).append(",");
        sb.append("createTime=").append(this.getCreateTime()).append(",");
        sb.append("modifyTime=").append(this.getModifyTime());
        sb.append("]");
        return sb.toString();
    }

}
