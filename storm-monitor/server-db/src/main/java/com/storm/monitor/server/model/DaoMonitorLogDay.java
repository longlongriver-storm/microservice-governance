package com.storm.monitor.server.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * bean DaoMonitorLogDay Title: 【DAO监控天汇总表】的PoJo类，也就是数据库表dao_monitor_log_day的映射类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
public class DaoMonitorLogDay extends DaoMonitorLog implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 复制所有属性到指定对象
     *
     * @param targetDaoMonitorLogDay 属性赋值目标对象
     */
    public void copyPropertiesTo(DaoMonitorLogDay targetDaoMonitorLogDay) {
        if (targetDaoMonitorLogDay == null) {
            return;
        }
        targetDaoMonitorLogDay.setLogTime(this.getLogTime());
        targetDaoMonitorLogDay.setMachineAddress(this.getMachineAddress());
        targetDaoMonitorLogDay.setServiceName(this.getServiceName());
        targetDaoMonitorLogDay.setSuccessCount(this.getSuccessCount());
        targetDaoMonitorLogDay.setFailureCount(this.getFailureCount());
        targetDaoMonitorLogDay.setAvgElapsed(this.getAvgElapsed());
        targetDaoMonitorLogDay.setMaxElapsed(this.getMaxElapsed());
        targetDaoMonitorLogDay.setMinElapsed(this.getMinElapsed());
        targetDaoMonitorLogDay.setLastestErrorMsg(this.getLastestErrorMsg());
    }

    public boolean equals(Object object) {
        if (!(object instanceof DaoMonitorLogDay)) {
            return false;
        }
        DaoMonitorLogDay daoMonitorLogDay2 = (DaoMonitorLogDay) object;
        return new EqualsBuilder()
            .appendSuper(super.equals(object))
            .append(this.getLogTime(), daoMonitorLogDay2.getLogTime())
            .append(this.getMachineAddress(), daoMonitorLogDay2.getMachineAddress())
            .append(this.getServiceName(), daoMonitorLogDay2.getServiceName())
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
