package com.storm.monitor.server.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * bean ServiceMonitorLogDay Title:
 * 【服务监控天汇总表】的PoJo类，也就是数据库表service_monitor_log_day的映射类 
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
public class ServiceMonitorLogDay extends ServiceMonitorLog implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 复制所有属性到指定对象
     *
     * @param targetServiceMonitorLogDay 属性赋值目标对象
     */
    public void copyPropertiesTo(ServiceMonitorLogDay targetServiceMonitorLogDay) {
        if (targetServiceMonitorLogDay == null) {
            return;
        }
        targetServiceMonitorLogDay.setLogTime(this.getLogTime());
        targetServiceMonitorLogDay.setMachineAddress(this.getMachineAddress());
        targetServiceMonitorLogDay.setServiceName(this.getServiceName());
        targetServiceMonitorLogDay.setSuccessCount(this.getSuccessCount());
        targetServiceMonitorLogDay.setFailureCount(this.getFailureCount());
        targetServiceMonitorLogDay.setAvgElapsed(this.getAvgElapsed());
        targetServiceMonitorLogDay.setMaxElapsed(this.getMaxElapsed());
        targetServiceMonitorLogDay.setMinElapsed(this.getMinElapsed());
        targetServiceMonitorLogDay.setLastestErrorMsg(this.getLastestErrorMsg());
        targetServiceMonitorLogDay.setLastestBizErrorCode(this.getLastestBizErrorCode());
        targetServiceMonitorLogDay.setLastestBizErrorMsg(this.getLastestBizErrorMsg());
        targetServiceMonitorLogDay.setBizFailureCount(this.getBizFailureCount());
    }

    public boolean equals(Object object) {
        if (!(object instanceof ServiceMonitorLogDay)) {
            return false;
        }
        ServiceMonitorLogDay serviceMonitorLogDay2 = (ServiceMonitorLogDay) object;
        return new EqualsBuilder()
            .appendSuper(super.equals(object))
            .append(this.getLogTime(), serviceMonitorLogDay2.getLogTime())
            .append(this.getMachineAddress(), serviceMonitorLogDay2.getMachineAddress())
            .append(this.getServiceName(), serviceMonitorLogDay2.getServiceName())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(-528253723, -475504089)
            .appendSuper(super.hashCode())
            .append(this.getLogTime())
            .append(this.getMachineAddress())
            .append(this.getServiceName())
            .toHashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("logTime=").append(this.getLogTime()).append(",");
        sb.append("machineAddress=").append(this.getMachineAddress()).append(",");
        sb.append("serviceName=").append(this.getServiceName()).append(",");
        sb.append("successCount=").append(this.getSuccessCount()).append(",");
        sb.append("failureCount=").append(this.getFailureCount()).append(",");
        sb.append("avgElapsed=").append(this.getAvgElapsed()).append(",");
        sb.append("maxElapsed=").append(this.getMaxElapsed()).append(",");
        sb.append("minElapsed=").append(this.getMinElapsed()).append(",");
        sb.append("lastestErrorMsg=").append(this.getLastestErrorMsg());
        sb.append("lastestBizErrorCode=").append(this.getLastestBizErrorCode()).append(",");
        sb.append("lastestBizErrorMsg=").append(this.getLastestBizErrorMsg()).append(",");
        sb.append("bizFailureCount=").append(this.getBizFailureCount());
        sb.append("]");
        return sb.toString();
    }

}
