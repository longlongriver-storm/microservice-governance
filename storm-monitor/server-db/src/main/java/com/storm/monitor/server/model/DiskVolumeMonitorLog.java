package com.storm.monitor.server.model;

import com.storm.monitor.core.entity.DiskVolumeInfo;
import com.storm.monitor.core.entity.SystemStatusInfo;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * bean DiskVolumeMonitorLog Title:
 * 【磁盘监控日志表】的PoJo类，也就是数据库表disk_volume_monitor_log的映射类 
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
public class DiskVolumeMonitorLog implements java.io.Serializable {

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
     * 磁盘卷ID
     */
    private java.lang.String volumeId;
    /**
     * 磁盘卷总容量
     */
    private java.lang.Long volumeTotal;
    /**
     * 磁盘卷未分配容量
     */
    private java.lang.Long volumeFree;
    /**
     * 磁盘卷可用容量
     */
    private java.lang.Long volumeUsable;

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

    public java.lang.String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(java.lang.String volumeId) {
        this.volumeId = volumeId;
    }

    public java.lang.Long getVolumeTotal() {
        return volumeTotal;
    }

    public void setVolumeTotal(java.lang.Long volumeTotal) {
        this.volumeTotal = volumeTotal;
    }

    public java.lang.Long getVolumeFree() {
        return volumeFree;
    }

    public void setVolumeFree(java.lang.Long volumeFree) {
        this.volumeFree = volumeFree;
    }

    public java.lang.Long getVolumeUsable() {
        return volumeUsable;
    }

    public void setVolumeUsable(java.lang.Long volumeUsable) {
        this.volumeUsable = volumeUsable;
    }

    public static List<DiskVolumeMonitorLog> buildDiskVolumeMonitorLogs(SystemMonitorLog systemMonitorLog, SystemStatusInfo status) {
        List<DiskVolumeMonitorLog> result = new ArrayList();
        if (status.getDisk() != null && status.getDisk().getDiskVolumes() != null) {
            for (DiskVolumeInfo vol : status.getDisk().getDiskVolumes()) {
                DiskVolumeMonitorLog dvml=new DiskVolumeMonitorLog();
                dvml.setLogTime(systemMonitorLog.getLogTime());
                dvml.setMachineAddress(systemMonitorLog.getMachineAddress());
                dvml.setVolumeFree(vol.getFree());
                dvml.setVolumeId(vol.getId());
                dvml.setVolumeTotal(vol.getTotal());
                dvml.setVolumeUsable(vol.getUsable());
                result.add(dvml);
            }
        }
        return result;
    }

    /**
     * 复制所有属性到指定对象
     *
     * @param targetDiskVolumeMonitorLog 属性赋值目标对象
     */
    public void copyPropertiesTo(DiskVolumeMonitorLog targetDiskVolumeMonitorLog) {
        if (targetDiskVolumeMonitorLog == null) {
            return;
        }
        targetDiskVolumeMonitorLog.setLogTime(this.getLogTime());
        targetDiskVolumeMonitorLog.setMachineAddress(this.getMachineAddress());
        targetDiskVolumeMonitorLog.setVolumeId(this.getVolumeId());
        targetDiskVolumeMonitorLog.setVolumeTotal(this.getVolumeTotal());
        targetDiskVolumeMonitorLog.setVolumeFree(this.getVolumeFree());
        targetDiskVolumeMonitorLog.setVolumeUsable(this.getVolumeUsable());
    }

    public boolean equals(Object object) {
        if (!(object instanceof DiskVolumeMonitorLog)) {
            return false;
        }
        DiskVolumeMonitorLog diskVolumeMonitorLog2 = (DiskVolumeMonitorLog) object;
        return new EqualsBuilder()
            .appendSuper(super.equals(object))
            .append(this.logTime, diskVolumeMonitorLog2.logTime)
            .append(this.machineAddress, diskVolumeMonitorLog2.machineAddress)
            .append(this.volumeId, diskVolumeMonitorLog2.volumeId)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(-528253723, -475504089)
            .appendSuper(super.hashCode())
            .append(this.logTime)
            .append(this.machineAddress)
            .append(this.volumeId)
            .toHashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("logTime=").append(this.getLogTime()).append(",");
        sb.append("machineAddress=").append(this.getMachineAddress()).append(",");
        sb.append("volumeId=").append(this.getVolumeId()).append(",");
        sb.append("volumeTotal=").append(this.getVolumeTotal()).append(",");
        sb.append("volumeFree=").append(this.getVolumeFree()).append(",");
        sb.append("volumeUsable=").append(this.getVolumeUsable());
        sb.append("]");
        return sb.toString();
    }

}
