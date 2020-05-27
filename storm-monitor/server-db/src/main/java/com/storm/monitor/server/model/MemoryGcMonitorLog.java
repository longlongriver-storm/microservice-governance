package com.storm.monitor.server.model;

import com.storm.monitor.core.entity.GcInfo;
import com.storm.monitor.core.entity.SystemStatusInfo;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * bean MemoryGcMonitorLog Title:
 * 【内存GC监控日志表】的PoJo类，也就是数据库表memory_gc_monitor_log的映射类 
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
public class MemoryGcMonitorLog implements java.io.Serializable {

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
     * GC名称
     */
    private java.lang.String gcName;
    /**
     * GC次数
     */
    private java.lang.Long gcCount;
    /**
     * GC耗时
     */
    private java.lang.Long gcTime;

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

    public java.lang.String getGcName() {
        return gcName;
    }

    public void setGcName(java.lang.String gcName) {
        this.gcName = gcName;
    }

    public java.lang.Long getGcCount() {
        return gcCount;
    }

    public void setGcCount(java.lang.Long gcCount) {
        this.gcCount = gcCount;
    }

    public java.lang.Long getGcTime() {
        return gcTime;
    }

    public void setGcTime(java.lang.Long gcTime) {
        this.gcTime = gcTime;
    }
    
    public static List<MemoryGcMonitorLog> buildMemoryGcMonitorLogs(SystemMonitorLog systemMonitorLog,SystemStatusInfo status){
        List<MemoryGcMonitorLog> result=new ArrayList();
        if(status.getMemory()!=null && status.getMemory().getGcs()!=null){
            for(GcInfo gc:status.getMemory().getGcs()){
                MemoryGcMonitorLog mgc=new MemoryGcMonitorLog();
                mgc.setLogTime(systemMonitorLog.getLogTime());
                mgc.setMachineAddress(systemMonitorLog.getMachineAddress());
                mgc.setGcCount(gc.getCount());
                mgc.setGcName(gc.getName());
                mgc.setGcTime(gc.getTime());
                result.add(mgc);
            }
        }
        
        return result;
    }

    /**
     * 复制所有属性到指定对象
     *
     * @param targetMemoryGcMonitorLog 属性赋值目标对象
     */
    public void copyPropertiesTo(MemoryGcMonitorLog targetMemoryGcMonitorLog) {
        if (targetMemoryGcMonitorLog == null) {
            return;
        }
        targetMemoryGcMonitorLog.setLogTime(this.getLogTime());
        targetMemoryGcMonitorLog.setMachineAddress(this.getMachineAddress());
        targetMemoryGcMonitorLog.setGcName(this.getGcName());
        targetMemoryGcMonitorLog.setGcCount(this.getGcCount());
        targetMemoryGcMonitorLog.setGcTime(this.getGcTime());
    }

    public boolean equals(Object object) {
        if (!(object instanceof MemoryGcMonitorLog)) {
            return false;
        }
        MemoryGcMonitorLog memoryGcMonitorLog2 = (MemoryGcMonitorLog) object;
        return new EqualsBuilder()
            .appendSuper(super.equals(object))
            .append(this.logTime, memoryGcMonitorLog2.logTime)
            .append(this.machineAddress, memoryGcMonitorLog2.machineAddress)
            .append(this.gcName, memoryGcMonitorLog2.gcName)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(-528253723, -475504089)
            .appendSuper(super.hashCode())
            .append(this.logTime)
            .append(this.machineAddress)
            .append(this.gcName)
            .toHashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("logTime=").append(this.getLogTime()).append(",");
        sb.append("machineAddress=").append(this.getMachineAddress()).append(",");
        sb.append("gcName=").append(this.getGcName()).append(",");
        sb.append("gcCount=").append(this.getGcCount()).append(",");
        sb.append("gcTime=").append(this.getGcTime());
        sb.append("]");
        return sb.toString();
    }

}
