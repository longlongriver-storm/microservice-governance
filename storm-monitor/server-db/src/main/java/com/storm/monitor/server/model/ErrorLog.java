package com.storm.monitor.server.model;

import com.storm.monitor.core.util.StringUtil;
import com.storm.monitor.core.entity.MessageTree;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * bean ErrorLog Title: 【异常信息汇总表】的PoJo类，也就是数据库表error_log的映射类 
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2018-03-15 10:58
 *
 */
public class ErrorLog implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;
    /**
     * 日志时间
     */
    private java.util.Date logTime;
    /**
     * 主机地址
     */
    private java.lang.String machineAddress;
    /**
     * 1：业务错误，0：系统错误
     */
    private java.lang.Integer errorType;
    /**
     * 调用链ID
     */
    private java.lang.String traceId;
    /**
     * 用户标识
     */
    private java.lang.String userKey;
    /**
     * 错误代码
     */
    private java.lang.String errorCode;
    /**
     * 错误信息
     */
    private java.lang.String errorMsg;
    /**
     * 服务名称
     */
    private java.lang.String serviceName;

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

    public java.lang.String getMachineAddress() {
        return machineAddress;
    }

    public void setMachineAddress(java.lang.String machineAddress) {
        this.machineAddress = machineAddress;
    }

    public java.lang.Integer getErrorType() {
        return errorType;
    }

    public void setErrorType(java.lang.Integer errorType) {
        this.errorType = errorType;
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

    public java.lang.String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(java.lang.String errorCode) {
        this.errorCode = errorCode;
    }

    public java.lang.String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(java.lang.String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public java.lang.String getServiceName() {
        return serviceName;
    }

    public void setServiceName(java.lang.String serviceName) {
        this.serviceName = serviceName;
    }
    
    public static ErrorLog buildErrorLog(MessageTree event){
        ErrorLog errorLog=new ErrorLog();
        
        String str = event.getMessage();
        int idx = str.indexOf(",");
        errorLog.setLogTime(new Date(Long.valueOf(str.substring(0, idx))));
        errorLog.setMachineAddress(event.getIpAddress());
        str = str.substring(idx + 1);  //排除时间，因为时间已经通过timestamp字段过来了
        
        idx = str.indexOf(",");
        errorLog.setServiceName(str.substring(0, idx));
        str = str.substring(idx + 1);  //排除服务名称
        
        idx = str.indexOf(",");
        errorLog.setErrorType(new Integer(str.substring(0, idx)));
        str = str.substring(idx + 1);  //排除错误类型
        

        errorLog.setTraceId(StringUtil.getSubStr(str, "traceId=", ",userKey="));
        errorLog.setUserKey(StringUtil.getSubStr(str, "userKey=", ",errCode="));
        errorLog.setErrorCode(StringUtil.getSubStr(str, "errCode=", ",errMsg="));
        idx = str.indexOf(",errMsg=")+",errMsg=".length();
        errorLog.setErrorMsg(str.substring(idx, str.length()-1));
        
        return errorLog;
    }

    /**
     * 复制所有属性到指定对象
     *
     * @param targetErrorLog 属性赋值目标对象
     */
    public void copyPropertiesTo(ErrorLog targetErrorLog) {
        if (targetErrorLog == null) {
            return;
        }
        targetErrorLog.setLogTime(this.getLogTime());
        targetErrorLog.setMachineAddress(this.getMachineAddress());
        targetErrorLog.setErrorType(this.getErrorType());
        targetErrorLog.setTraceId(this.getTraceId());
        targetErrorLog.setUserKey(this.getUserKey());
        targetErrorLog.setErrorCode(this.getErrorCode());
        targetErrorLog.setErrorMsg(this.getErrorMsg());
        targetErrorLog.setServiceName(this.getServiceName());
    }

    public boolean equals(Object object) {
        if (!(object instanceof ErrorLog)) {
            return false;
        }
        ErrorLog errorLog2 = (ErrorLog) object;
        return new EqualsBuilder()
            .appendSuper(super.equals(object))
            .append(this.logTime, errorLog2.logTime)
            .append(this.machineAddress, errorLog2.machineAddress)
            .append(this.errorType, errorLog2.errorType)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(-528253723, -475504089)
            .appendSuper(super.hashCode())
            .append(this.logTime)
            .append(this.machineAddress)
            .append(this.errorType)
            .toHashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("logTime=").append(this.getLogTime()).append(",");
        sb.append("machineAddress=").append(this.getMachineAddress()).append(",");
        sb.append("errorType=").append(this.getErrorType()).append(",");
        sb.append("traceId=").append(this.getTraceId()).append(",");
        sb.append("userKey=").append(this.getUserKey()).append(",");
        sb.append("errorCode=").append(this.getErrorCode()).append(",");
        sb.append("errorMsg=").append(this.getErrorMsg());
        sb.append("serviceName=").append(this.getServiceName());
        sb.append("]");
        return sb.toString();
    }

}
