package com.storm.monitor.server.model;

import com.storm.monitor.core.util.StringUtil;
import com.storm.monitor.core.entity.MessageTree;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * bean ServiceMonitorLog Title: 【服务监控日志表】的PoJo类，也就是数据库表service_monitor_log的映射类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-10 14:59
 *
 */
public class ServiceMonitorLog implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 日志时间
     */
    private java.util.Date logTime;
    /**
     * 主机地址
     */
    private java.lang.String machineAddress;
    /**
     * 服务名称
     */
    private java.lang.String serviceName;
    /**
     * 成功次数
     */
    private java.lang.Long successCount;
    /**
     * 失败次数
     */
    private java.lang.Long failureCount;
    /**
     * 平均延时
     */
    private java.lang.Long avgElapsed;
    /**
     * 最大延时
     */
    private java.lang.Long maxElapsed;
    /**
     * 最小延时
     */
    private java.lang.Long minElapsed;
    /**
     * 最新错误类
     */
    private java.lang.String lastestErrorMsg;
    /**
     * 最近业务错误代码
     */
    private java.lang.String lastestBizErrorCode;
    /**
     * 最近业务错误信息
     */
    private java.lang.String lastestBizErrorMsg;
    /**
     * biz_failure_count
     */
    private java.lang.Long bizFailureCount;

    public java.util.Date getLogTime() {
        return logTime;
    }

    public void setLogTime(java.util.Date logTime) {
        this.logTime = logTime;
    }

    public java.lang.String getMachineAddress() {
        return machineAddress;
    }

    public void setMachineAddress(java.lang.String machineAddress) {
        this.machineAddress = machineAddress;
    }

    public java.lang.String getServiceName() {
        return serviceName;
    }

    public void setServiceName(java.lang.String serviceName) {
        this.serviceName = serviceName;
    }

    public java.lang.Long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(java.lang.Long successCount) {
        this.successCount = successCount;
    }

    public java.lang.Long getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(java.lang.Long failureCount) {
        this.failureCount = failureCount;
    }

    public java.lang.Long getAvgElapsed() {
        return avgElapsed;
    }

    public void setAvgElapsed(java.lang.Long avgElapsed) {
        this.avgElapsed = avgElapsed;
    }

    public java.lang.Long getMaxElapsed() {
        return maxElapsed;
    }

    public void setMaxElapsed(java.lang.Long maxElapsed) {
        this.maxElapsed = maxElapsed;
    }

    public java.lang.Long getMinElapsed() {
        return minElapsed;
    }

    public void setMinElapsed(java.lang.Long minElapsed) {
        this.minElapsed = minElapsed;
    }

    public java.lang.String getLastestErrorMsg() {
        return lastestErrorMsg;
    }

    public void setLastestErrorMsg(java.lang.String lastestErrorMsg) {
        this.lastestErrorMsg = lastestErrorMsg;
    }

    public java.lang.String getLastestBizErrorCode() {
        return lastestBizErrorCode;
    }

    public void setLastestBizErrorCode(java.lang.String lastestBizErrorCode) {
        this.lastestBizErrorCode = lastestBizErrorCode;
    }

    public java.lang.String getLastestBizErrorMsg() {
        return lastestBizErrorMsg;
    }

    public void setLastestBizErrorMsg(java.lang.String lastestBizErrorMsg) {
        this.lastestBizErrorMsg = lastestBizErrorMsg;
    }

    public java.lang.Long getBizFailureCount() {
        return bizFailureCount;
    }

    public void setBizFailureCount(java.lang.Long bizFailureCount) {
        this.bizFailureCount = bizFailureCount;
    }

    /**
     * 根据MessageTree对象构建一个ServiceMonitorLog消息对象
     *
     * @param event
     * @return
     */
    public static ServiceMonitorLog buildServiceMonitorLog(MessageTree event) {
        //"{},{},success={},failure={},avgElapsed={},maxElapsed={},minElapsed={},lastestErrorMsg={};"
        ServiceMonitorLog serviceMonitorLog = new ServiceMonitorLog();
        
        String str = event.getMessage();
        int idx = str.indexOf(",");
        serviceMonitorLog.setLogTime(new Date(Long.valueOf(str.substring(0, idx))));
        serviceMonitorLog.setMachineAddress(event.getIpAddress());
        str = str.substring(idx + 1);  //排除时间

        idx = str.indexOf(",");
        serviceMonitorLog.setServiceName(str.substring(0, idx));
        str = str.substring(idx + 1);  //排除服务名称

        idx = str.indexOf(",");
        serviceMonitorLog.setSuccessCount(Long.valueOf((str.substring(0, idx).split("="))[1]));
        str = str.substring(idx + 1);  //排除成功数
        
        idx = str.indexOf(",");
        serviceMonitorLog.setFailureCount(Long.valueOf((str.substring(0, idx).split("="))[1]));
        str = str.substring(idx + 1); 
        
        idx = str.indexOf(",");
        serviceMonitorLog.setBizFailureCount(Long.valueOf((str.substring(0, idx).split("="))[1]));
        str = str.substring(idx + 1); 
        
        idx = str.indexOf(",");
        serviceMonitorLog.setAvgElapsed(Long.valueOf((str.substring(0, idx).split("="))[1]));
        str = str.substring(idx + 1);  

        idx = str.indexOf(",");
        serviceMonitorLog.setMaxElapsed(Long.valueOf((str.substring(0, idx).split("="))[1]));
        str = str.substring(idx + 1); 

        idx = str.indexOf(",");
        serviceMonitorLog.setMinElapsed(Long.valueOf((str.substring(0, idx).split("="))[1]));

        serviceMonitorLog.setLastestErrorMsg(StringUtil.getSubStr(str, "lastestErrorMsg=", ",lastestBizErrorMsg="));
        serviceMonitorLog.setLastestBizErrorCode(StringUtil.getSubStr(str, "lastestBizErrorCode=", ",lastestErrorMsg="));
        
        idx = str.indexOf(",lastestBizErrorMsg=")+",lastestBizErrorMsg=".length();
        serviceMonitorLog.setLastestBizErrorMsg(str.substring(idx, str.length()-1));
        
        return serviceMonitorLog;
    }

    /**
     * 复制所有属性到指定对象
     *
     * @param targetServiceMonitorLog 属性赋值目标对象
     */
    public void copyPropertiesTo(ServiceMonitorLog targetServiceMonitorLog) {
        if (targetServiceMonitorLog == null) {
            return;
        }
        targetServiceMonitorLog.setLogTime(this.getLogTime());
        targetServiceMonitorLog.setMachineAddress(this.getMachineAddress());
        targetServiceMonitorLog.setServiceName(this.getServiceName());
        targetServiceMonitorLog.setSuccessCount(this.getSuccessCount());
        targetServiceMonitorLog.setFailureCount(this.getFailureCount());
        targetServiceMonitorLog.setAvgElapsed(this.getAvgElapsed());
        targetServiceMonitorLog.setMaxElapsed(this.getMaxElapsed());
        targetServiceMonitorLog.setMinElapsed(this.getMinElapsed());
        targetServiceMonitorLog.setLastestErrorMsg(this.getLastestErrorMsg());
        targetServiceMonitorLog.setLastestBizErrorCode(this.getLastestBizErrorCode());
        targetServiceMonitorLog.setLastestBizErrorMsg(this.getLastestBizErrorMsg());
        targetServiceMonitorLog.setBizFailureCount(this.getBizFailureCount());
    }

    public boolean equals(Object object) {
        if (!(object instanceof ServiceMonitorLog)) {
            return false;
        }
        ServiceMonitorLog serviceMonitorLog2 = (ServiceMonitorLog) object;
        return new EqualsBuilder()
            .appendSuper(super.equals(object))
            .append(this.logTime, serviceMonitorLog2.logTime)
            .append(this.machineAddress, serviceMonitorLog2.machineAddress)
            .append(this.serviceName, serviceMonitorLog2.serviceName)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(-528253723, -475504089)
            .appendSuper(super.hashCode())
            .append(this.logTime)
            .append(this.machineAddress)
            .append(this.serviceName)
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
