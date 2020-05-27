package com.storm.monitor.server.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * bean DaoMonitorLogHour Title:
 * 【DAO监控小时汇总表】的PoJo类，也就是数据库表dao_monitor_log_hour的映射类 
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
public class DaoMonitorLogHour extends DaoMonitorLog implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 复制所有属性到指定对象
     *
     * @param targetDaoMonitorLogHour 属性赋值目标对象
     */
    public void copyPropertiesTo(DaoMonitorLogHour targetDaoMonitorLogHour) {
        if (targetDaoMonitorLogHour == null) {
            return;
        }
        targetDaoMonitorLogHour.setLogTime(this.getLogTime());
        targetDaoMonitorLogHour.setMachineAddress(this.getMachineAddress());
        targetDaoMonitorLogHour.setServiceName(this.getServiceName());
        targetDaoMonitorLogHour.setSuccessCount(this.getSuccessCount());
        targetDaoMonitorLogHour.setFailureCount(this.getFailureCount());
        targetDaoMonitorLogHour.setAvgElapsed(this.getAvgElapsed());
        targetDaoMonitorLogHour.setMaxElapsed(this.getMaxElapsed());
        targetDaoMonitorLogHour.setMinElapsed(this.getMinElapsed());
        targetDaoMonitorLogHour.setLastestErrorMsg(this.getLastestErrorMsg());
    }

    public boolean equals(Object object) {
        if (!(object instanceof DaoMonitorLogHour)) {
            return false;
        }
        DaoMonitorLogHour daoMonitorLogHour2 = (DaoMonitorLogHour) object;
        return new EqualsBuilder()
            .appendSuper(super.equals(object))
            .append(this.getLogTime(), daoMonitorLogHour2.getLogTime())
            .append(this.getMachineAddress(), daoMonitorLogHour2.getMachineAddress())
            .append(this.getServiceName(), daoMonitorLogHour2.getServiceName())
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
        sb.append("]");
        return sb.toString();
    }

}
