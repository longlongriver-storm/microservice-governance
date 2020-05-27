package com.storm.monitor.server.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * bean ServiceMonitorLogHour Title:
 * 【服务监控小时汇总表】的PoJo类，也就是数据库表service_monitor_log_hour的映射类 
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
public class ServiceMonitorLogHour extends ServiceMonitorLog implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 复制所有属性到指定对象
     *
     * @param targetServiceMonitorLogHour 属性赋值目标对象
     */
    public void copyPropertiesTo(ServiceMonitorLogHour targetServiceMonitorLogHour) {
        if (targetServiceMonitorLogHour == null) {
            return;
        }
        targetServiceMonitorLogHour.setLogTime(this.getLogTime());
        targetServiceMonitorLogHour.setMachineAddress(this.getMachineAddress());
        targetServiceMonitorLogHour.setServiceName(this.getServiceName());
        targetServiceMonitorLogHour.setSuccessCount(this.getSuccessCount());
        targetServiceMonitorLogHour.setFailureCount(this.getFailureCount());
        targetServiceMonitorLogHour.setAvgElapsed(this.getAvgElapsed());
        targetServiceMonitorLogHour.setMaxElapsed(this.getMaxElapsed());
        targetServiceMonitorLogHour.setMinElapsed(this.getMinElapsed());
        targetServiceMonitorLogHour.setLastestErrorMsg(this.getLastestErrorMsg());
        targetServiceMonitorLogHour.setLastestBizErrorCode(this.getLastestBizErrorCode());
        targetServiceMonitorLogHour.setLastestBizErrorMsg(this.getLastestBizErrorMsg());
        targetServiceMonitorLogHour.setBizFailureCount(this.getBizFailureCount());
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ServiceMonitorLogHour)) {
            return false;
        }
        ServiceMonitorLogHour serviceMonitorLogHour2 = (ServiceMonitorLogHour) object;
        return new EqualsBuilder()
            .appendSuper(super.equals(object))
            .append(this.getLogTime(), serviceMonitorLogHour2.getLogTime())
            .append(this.getMachineAddress(), serviceMonitorLogHour2.getMachineAddress())
            .append(this.getServiceName(), serviceMonitorLogHour2.getServiceName())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(-528253723, -475504089)
            .appendSuper(super.hashCode())
            .append(this.getLogTime())
            .append(this.getMachineAddress())
            .append(this.getServiceName())
            .toHashCode();
    }

    @Override
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
