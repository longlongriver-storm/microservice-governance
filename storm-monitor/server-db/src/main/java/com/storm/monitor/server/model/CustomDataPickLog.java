package com.storm.monitor.server.model;

import com.storm.monitor.core.util.StringUtil;
import com.storm.monitor.core.entity.MessageTree;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * bean CustomDataPickLog Title:
 * 【业务数据采集汇总表】的PoJo类，也就是数据库表custom_data_pick_log的映射类 
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2018-09-27 11:00
 *
 */
public class CustomDataPickLog implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;
    /**
     * 日志时间
     */
    private java.util.Date logTime;
    /**
     * 服务名称
     */
    private java.lang.String serviceName;
    /**
     * 主机地址
     */
    private java.lang.String machineAddress;
    /**
     * 调用链ID
     */
    private java.lang.String traceId;
    /**
     * 用户标识
     */
    private java.lang.String userKey;
    /**
     * 抓取数据
     */
    private java.lang.String pickData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.util.Date getLogTime() {
        return logTime;
    }

    public void setLogTime(java.util.Date logTime) {
        this.logTime = logTime;
    }

    public java.lang.String getServiceName() {
        return serviceName;
    }

    public void setServiceName(java.lang.String serviceName) {
        this.serviceName = serviceName;
    }

    public java.lang.String getMachineAddress() {
        return machineAddress;
    }

    public void setMachineAddress(java.lang.String machineAddress) {
        this.machineAddress = machineAddress;
    }

    public java.lang.String getTraceId() {
        return traceId;
    }

    public void setTraceId(java.lang.String traceId) {
        this.traceId = traceId;
    }

    public java.lang.String getUserKey() {
        return userKey;
    }

    public void setUserKey(java.lang.String userKey) {
        this.userKey = userKey;
    }

    public java.lang.String getPickData() {
        return pickData;
    }

    public void setPickData(java.lang.String pickData) {
        this.pickData = pickData;
    }
    
    public static CustomDataPickLog buildCustomDataPickLog(MessageTree event){
        CustomDataPickLog customDataPickLog=new CustomDataPickLog();
        
        String str = event.getMessage();
        int idx = str.indexOf(",");
        customDataPickLog.setLogTime(new Date(Long.valueOf(str.substring(0, idx))));
        customDataPickLog.setMachineAddress(event.getIpAddress());
        str = str.substring(idx + 1);  //排除时间

        idx = str.indexOf(",");
        customDataPickLog.setServiceName(str.substring(0, idx));
        str = str.substring(idx + 1);  //排除服务名称
        
        customDataPickLog.setTraceId(StringUtil.getSubStr(str, "traceId=", ",userKey="));
        customDataPickLog.setUserKey(StringUtil.getSubStr(str, "userKey=", ",errCode="));
        idx = str.indexOf(",customPickData=")+",customPickData=".length();
        customDataPickLog.setPickData(str.substring(idx, str.length()-1));

        return customDataPickLog;
    }

    /**
     * 复制所有属性到指定对象
     *
     * @param targetCustomDataPickLog 属性赋值目标对象
     */
    public void copyPropertiesTo(CustomDataPickLog targetCustomDataPickLog) {
        if (targetCustomDataPickLog == null) {
            return;
        }
        targetCustomDataPickLog.setLogTime(this.getLogTime());
        targetCustomDataPickLog.setServiceName(this.getServiceName());
        targetCustomDataPickLog.setMachineAddress(this.getMachineAddress());
        targetCustomDataPickLog.setTraceId(this.getTraceId());
        targetCustomDataPickLog.setUserKey(this.getUserKey());
        targetCustomDataPickLog.setPickData(this.getPickData());
    }

    public boolean equals(Object object) {
        if (!(object instanceof CustomDataPickLog)) {
            return false;
        }
        CustomDataPickLog customDataPickLog2 = (CustomDataPickLog) object;
        return new EqualsBuilder()
            .appendSuper(super.equals(object))
            .append(this.logTime, customDataPickLog2.logTime)
            .append(this.serviceName, customDataPickLog2.serviceName)
            .append(this.machineAddress, customDataPickLog2.machineAddress)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(-528253723, -475504089)
            .appendSuper(super.hashCode())
            .append(this.logTime)
            .append(this.serviceName)
            .append(this.machineAddress)
            .toHashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("logTime=").append(this.getLogTime()).append(",");
        sb.append("serviceName=").append(this.getServiceName()).append(",");
        sb.append("machineAddress=").append(this.getMachineAddress()).append(",");
        sb.append("traceId=").append(this.getTraceId()).append(",");
        sb.append("userKey=").append(this.getUserKey()).append(",");
        sb.append("pickData=").append(this.getPickData());
        sb.append("]");
        return sb.toString();
    }

}
