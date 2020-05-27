package com.storm.monitor.server.model;

import com.storm.monitor.core.entity.DiskVolumeInfo;
import com.storm.monitor.core.entity.MessageTree;
import com.storm.monitor.core.entity.SystemStatusInfo;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * bean SystemMonitorLog Title: 【系统监控日志表】的PoJo类，也就是数据库表system_monitor_log的映射类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-30 07:36
 *
 */
public class SystemMonitorLog implements java.io.Serializable {

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
     * 磁盘总容量
     */
    private java.lang.Long diskTotal;
    /**
     * 磁盘未分配容量
     */
    private java.lang.Long diskFree;
    /**
     * 磁盘可用容量
     */
    private java.lang.Long diskUsable;
    /**
     * 最大内存量
     */
    private java.lang.Long memoryMax;
    /**
     * 内存总量
     */
    private java.lang.Long memoryTotal;
    /**
     * 空闲内存量
     */
    private java.lang.Long memoryFree;
    /**
     * 堆内存量
     */
    private java.lang.Long memoryHeapusage;
    /**
     * 非堆内存量
     */
    private java.lang.Long memoryNonheapusage;
    /**
     * 操作系统的架构
     */
    private java.lang.String osArch;
    /**
     * 操作系统名称
     */
    private java.lang.String osName;
    /**
     * 操作系统的版本
     */
    private java.lang.String osVersion;
    /**
     * Java虚拟机可以使用的处理器数目
     */
    private java.lang.Integer osAvailableprocessors;
    /**
     * 最后一分钟内系统加载平均值
     */
    private java.lang.Float osSystemloadaverage;
    /**
     * 总物理内存量
     */
    private java.lang.Long osTotalphysicalmemory;
    /**
     * 未分配物理内存量
     */
    private java.lang.Long osFreephysicalmemory;
    /**
     * 总交换空间量
     */
    private java.lang.Long osTotalswapspace;
    /**
     * 未分配交换空间量
     */
    private java.lang.Long osFreeswapspace;
    /**
     * java到当前为止所占用的CPU处理时间
     */
    private java.lang.Long osProcesstime;
    /**
     * java运行进程保证可用的虚拟内存大小
     */
    private java.lang.Long osCommittedvirtualmemory;
    /**
     * java版本
     */
    private java.lang.String runtimeJavaversion;
    /**
     * JVM启动时间
     */
    private java.lang.Long runtimeStarttime;
    /**
     * JVM运行时长
     */
    private java.lang.Long runtimeUptime;
    /**
     * 系统用户目录
     */
    private java.lang.String runtimeUserdir;
    /**
     * 系统用户名称
     */
    private java.lang.String runtimeUsername;
    /**
     * 活动线程数
     */
    private java.lang.Integer threadActive;
    /**
     * APM监控相关线程数
     */
    private java.lang.Integer threadCatCount;
    /**
     * 当前总存活线程数
     */
    private java.lang.Integer threadCount;
    /**
     * 当前守护线程数
     */
    private java.lang.Integer threadDaemonCount;
    /**
     * 当前HTTP线程数
     */
    private java.lang.Integer threadHttp;
    /**
     * 峰值活动线程计数
     */
    private java.lang.Integer threadPeek;
    /**
     * 已启动线程数
     */
    private java.lang.Integer threadStarted;
    /**
     * 总启动线程数
     */
    private java.lang.Integer threadTotalStarted;

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

    public java.lang.Long getDiskTotal() {
        return diskTotal;
    }

    public void setDiskTotal(java.lang.Long diskTotal) {
        this.diskTotal = diskTotal;
    }

    public java.lang.Long getDiskFree() {
        return diskFree;
    }

    public void setDiskFree(java.lang.Long diskFree) {
        this.diskFree = diskFree;
    }

    public java.lang.Long getDiskUsable() {
        return diskUsable;
    }

    public void setDiskUsable(java.lang.Long diskUsable) {
        this.diskUsable = diskUsable;
    }

    public java.lang.Long getMemoryMax() {
        return memoryMax;
    }

    public void setMemoryMax(java.lang.Long memoryMax) {
        this.memoryMax = memoryMax;
    }

    public java.lang.Long getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(java.lang.Long memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public java.lang.Long getMemoryFree() {
        return memoryFree;
    }

    public void setMemoryFree(java.lang.Long memoryFree) {
        this.memoryFree = memoryFree;
    }

    public java.lang.Long getMemoryHeapusage() {
        return memoryHeapusage;
    }

    public void setMemoryHeapusage(java.lang.Long memoryHeapusage) {
        this.memoryHeapusage = memoryHeapusage;
    }

    public java.lang.Long getMemoryNonheapusage() {
        return memoryNonheapusage;
    }

    public void setMemoryNonheapusage(java.lang.Long memoryNonheapusage) {
        this.memoryNonheapusage = memoryNonheapusage;
    }

    public java.lang.String getOsArch() {
        return osArch;
    }

    public void setOsArch(java.lang.String osArch) {
        this.osArch = osArch;
    }

    public java.lang.String getOsName() {
        return osName;
    }

    public void setOsName(java.lang.String osName) {
        this.osName = osName;
    }

    public java.lang.String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(java.lang.String osVersion) {
        this.osVersion = osVersion;
    }

    public java.lang.Integer getOsAvailableprocessors() {
        return osAvailableprocessors;
    }

    public void setOsAvailableprocessors(java.lang.Integer osAvailableprocessors) {
        this.osAvailableprocessors = osAvailableprocessors;
    }

    public java.lang.Float getOsSystemloadaverage() {
        return osSystemloadaverage;
    }

    public void setOsSystemloadaverage(java.lang.Float osSystemloadaverage) {
        this.osSystemloadaverage = osSystemloadaverage;
    }

    public java.lang.Long getOsTotalphysicalmemory() {
        return osTotalphysicalmemory;
    }

    public void setOsTotalphysicalmemory(java.lang.Long osTotalphysicalmemory) {
        this.osTotalphysicalmemory = osTotalphysicalmemory;
    }

    public java.lang.Long getOsFreephysicalmemory() {
        return osFreephysicalmemory;
    }

    public void setOsFreephysicalmemory(java.lang.Long osFreephysicalmemory) {
        this.osFreephysicalmemory = osFreephysicalmemory;
    }

    public java.lang.Long getOsTotalswapspace() {
        return osTotalswapspace;
    }

    public void setOsTotalswapspace(java.lang.Long osTotalswapspace) {
        this.osTotalswapspace = osTotalswapspace;
    }

    public java.lang.Long getOsFreeswapspace() {
        return osFreeswapspace;
    }

    public void setOsFreeswapspace(java.lang.Long osFreeswapspace) {
        this.osFreeswapspace = osFreeswapspace;
    }

    public java.lang.Long getOsProcesstime() {
        return osProcesstime;
    }

    public void setOsProcesstime(java.lang.Long osProcesstime) {
        this.osProcesstime = osProcesstime;
    }

    public java.lang.Long getOsCommittedvirtualmemory() {
        return osCommittedvirtualmemory;
    }

    public void setOsCommittedvirtualmemory(java.lang.Long osCommittedvirtualmemory) {
        this.osCommittedvirtualmemory = osCommittedvirtualmemory;
    }

    public java.lang.String getRuntimeJavaversion() {
        return runtimeJavaversion;
    }

    public void setRuntimeJavaversion(java.lang.String runtimeJavaversion) {
        this.runtimeJavaversion = runtimeJavaversion;
    }

    public java.lang.Long getRuntimeStarttime() {
        return runtimeStarttime;
    }

    public void setRuntimeStarttime(java.lang.Long runtimeStarttime) {
        this.runtimeStarttime = runtimeStarttime;
    }

    public java.lang.Long getRuntimeUptime() {
        return runtimeUptime;
    }

    public void setRuntimeUptime(java.lang.Long runtimeUptime) {
        this.runtimeUptime = runtimeUptime;
    }

    public java.lang.String getRuntimeUserdir() {
        return runtimeUserdir;
    }

    public void setRuntimeUserdir(java.lang.String runtimeUserdir) {
        this.runtimeUserdir = runtimeUserdir;
    }

    public java.lang.String getRuntimeUsername() {
        return runtimeUsername;
    }

    public void setRuntimeUsername(java.lang.String runtimeUsername) {
        this.runtimeUsername = runtimeUsername;
    }

    public java.lang.Integer getThreadActive() {
        return threadActive;
    }

    public void setThreadActive(java.lang.Integer threadActive) {
        this.threadActive = threadActive;
    }

    public java.lang.Integer getThreadCatCount() {
        return threadCatCount;
    }

    public void setThreadCatCount(java.lang.Integer threadCatCount) {
        this.threadCatCount = threadCatCount;
    }

    public java.lang.Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(java.lang.Integer threadCount) {
        this.threadCount = threadCount;
    }

    public java.lang.Integer getThreadDaemonCount() {
        return threadDaemonCount;
    }

    public void setThreadDaemonCount(java.lang.Integer threadDaemonCount) {
        this.threadDaemonCount = threadDaemonCount;
    }

    public java.lang.Integer getThreadHttp() {
        return threadHttp;
    }

    public void setThreadHttp(java.lang.Integer threadHttp) {
        this.threadHttp = threadHttp;
    }

    public java.lang.Integer getThreadPeek() {
        return threadPeek;
    }

    public void setThreadPeek(java.lang.Integer threadPeek) {
        this.threadPeek = threadPeek;
    }

    public java.lang.Integer getThreadStarted() {
        return threadStarted;
    }

    public void setThreadStarted(java.lang.Integer threadStarted) {
        this.threadStarted = threadStarted;
    }

    public java.lang.Integer getThreadTotalStarted() {
        return threadTotalStarted;
    }

    public void setThreadTotalStarted(java.lang.Integer threadTotalStarted) {
        this.threadTotalStarted = threadTotalStarted;
    }
    
    public static SystemMonitorLog buildSystemMonitorLog(MessageTree event,SystemStatusInfo status){
        SystemMonitorLog systemMonitorLog=new SystemMonitorLog();
        String str = event.getMessage();
        int idx = str.indexOf(",");
        systemMonitorLog.setLogTime(new Date(Long.valueOf(str.substring(0, idx))));
        systemMonitorLog.setMachineAddress(event.getIpAddress());
        
        if(status.getDisk()!=null && status.getDisk().getDiskVolumes()!=null){
            for(DiskVolumeInfo vol:status.getDisk().getDiskVolumes()){
                systemMonitorLog.setDiskTotal((systemMonitorLog.getDiskTotal()==null?0:systemMonitorLog.getDiskTotal())+vol.getTotal());
                systemMonitorLog.setDiskFree((systemMonitorLog.getDiskFree()==null?0:systemMonitorLog.getDiskFree())+vol.getFree());
                systemMonitorLog.setDiskUsable((systemMonitorLog.getDiskUsable()==null?0:systemMonitorLog.getDiskUsable())+vol.getUsable());
            }
        }
        
        systemMonitorLog.setMemoryMax(status.getMemory().getMax());
        systemMonitorLog.setMemoryTotal(status.getMemory().getTotal());
        systemMonitorLog.setMemoryFree(status.getMemory().getFree());
        systemMonitorLog.setMemoryHeapusage(status.getMemory().getHeapUsage());
        systemMonitorLog.setMemoryNonheapusage(status.getMemory().getNonHeapUsage());
        systemMonitorLog.setOsArch(status.getOs().getArch());
        systemMonitorLog.setOsName(status.getOs().getName());
        systemMonitorLog.setOsVersion(status.getOs().getVersion());
        systemMonitorLog.setOsAvailableprocessors(status.getOs().getAvailableProcessors());
        systemMonitorLog.setOsSystemloadaverage(new Double(status.getOs().getSystemLoadAverage()).floatValue());
        systemMonitorLog.setOsTotalphysicalmemory(status.getOs().getTotalPhysicalMemory());
        systemMonitorLog.setOsFreephysicalmemory(status.getOs().getFreePhysicalMemory());
        systemMonitorLog.setOsTotalswapspace(status.getOs().getTotalSwapSpace());
        systemMonitorLog.setOsFreeswapspace(status.getOs().getFreeSwapSpace());
        systemMonitorLog.setOsProcesstime(status.getOs().getProcessTime());
        systemMonitorLog.setOsCommittedvirtualmemory(status.getOs().getCommittedVirtualMemory());
        systemMonitorLog.setRuntimeJavaversion(status.getRuntime().getJavaVersion());
        systemMonitorLog.setRuntimeStarttime(status.getRuntime().getStartTime());
        systemMonitorLog.setRuntimeUptime(status.getRuntime().getUpTime());
        systemMonitorLog.setRuntimeUserdir(status.getRuntime().getUserDir());
        systemMonitorLog.setRuntimeUsername(status.getRuntime().getUserName());
        systemMonitorLog.setThreadActive(status.getThread().getActiveThread());
        systemMonitorLog.setThreadCatCount(status.getThread().getCatThreadCount());
        systemMonitorLog.setThreadCount(status.getThread().getCount());
        systemMonitorLog.setThreadDaemonCount(status.getThread().getDaemonCount());
        systemMonitorLog.setThreadHttp(status.getThread().getHttpThread());
        systemMonitorLog.setThreadPeek(status.getThread().getPeekCount());
        systemMonitorLog.setThreadStarted(new Long(status.getThread().getStartedThread()).intValue());
        systemMonitorLog.setThreadTotalStarted(status.getThread().getTotalStartedCount());
        
        return systemMonitorLog;
    }

    /**
     * 复制所有属性到指定对象
     *
     * @param targetSystemMonitorLog 属性赋值目标对象
     */
    public void copyPropertiesTo(SystemMonitorLog targetSystemMonitorLog) {
        if (targetSystemMonitorLog == null) {
            return;
        }
        targetSystemMonitorLog.setLogTime(this.getLogTime());
        targetSystemMonitorLog.setMachineAddress(this.getMachineAddress());
        targetSystemMonitorLog.setDiskTotal(this.getDiskTotal());
        targetSystemMonitorLog.setDiskFree(this.getDiskFree());
        targetSystemMonitorLog.setDiskUsable(this.getDiskUsable());
        targetSystemMonitorLog.setMemoryMax(this.getMemoryMax());
        targetSystemMonitorLog.setMemoryTotal(this.getMemoryTotal());
        targetSystemMonitorLog.setMemoryFree(this.getMemoryFree());
        targetSystemMonitorLog.setMemoryHeapusage(this.getMemoryHeapusage());
        targetSystemMonitorLog.setMemoryNonheapusage(this.getMemoryNonheapusage());
        targetSystemMonitorLog.setOsArch(this.getOsArch());
        targetSystemMonitorLog.setOsName(this.getOsName());
        targetSystemMonitorLog.setOsVersion(this.getOsVersion());
        targetSystemMonitorLog.setOsAvailableprocessors(this.getOsAvailableprocessors());
        targetSystemMonitorLog.setOsSystemloadaverage(this.getOsSystemloadaverage());
        targetSystemMonitorLog.setOsTotalphysicalmemory(this.getOsTotalphysicalmemory());
        targetSystemMonitorLog.setOsFreephysicalmemory(this.getOsFreephysicalmemory());
        targetSystemMonitorLog.setOsTotalswapspace(this.getOsTotalswapspace());
        targetSystemMonitorLog.setOsFreeswapspace(this.getOsFreeswapspace());
        targetSystemMonitorLog.setOsProcesstime(this.getOsProcesstime());
        targetSystemMonitorLog.setOsCommittedvirtualmemory(this.getOsCommittedvirtualmemory());
        targetSystemMonitorLog.setRuntimeJavaversion(this.getRuntimeJavaversion());
        targetSystemMonitorLog.setRuntimeStarttime(this.getRuntimeStarttime());
        targetSystemMonitorLog.setRuntimeUptime(this.getRuntimeUptime());
        targetSystemMonitorLog.setRuntimeUserdir(this.getRuntimeUserdir());
        targetSystemMonitorLog.setRuntimeUsername(this.getRuntimeUsername());
        targetSystemMonitorLog.setThreadActive(this.getThreadActive());
        targetSystemMonitorLog.setThreadCatCount(this.getThreadCatCount());
        targetSystemMonitorLog.setThreadCount(this.getThreadCount());
        targetSystemMonitorLog.setThreadDaemonCount(this.getThreadDaemonCount());
        targetSystemMonitorLog.setThreadHttp(this.getThreadHttp());
        targetSystemMonitorLog.setThreadPeek(this.getThreadPeek());
        targetSystemMonitorLog.setThreadStarted(this.getThreadStarted());
        targetSystemMonitorLog.setThreadTotalStarted(this.getThreadTotalStarted());
    }

    public boolean equals(Object object) {
        if (!(object instanceof SystemMonitorLog)) {
            return false;
        }
        SystemMonitorLog systemMonitorLog2 = (SystemMonitorLog) object;
        return new EqualsBuilder()
            .appendSuper(super.equals(object))
            .append(this.logTime, systemMonitorLog2.logTime)
            .append(this.machineAddress, systemMonitorLog2.machineAddress)
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(-528253723, -475504089)
            .appendSuper(super.hashCode())
            .append(this.logTime)
            .append(this.machineAddress)
            .toHashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("logTime=").append(this.getLogTime()).append(",");
        sb.append("machineAddress=").append(this.getMachineAddress()).append(",");
        sb.append("diskTotal=").append(this.getDiskTotal()).append(",");
        sb.append("diskFree=").append(this.getDiskFree()).append(",");
        sb.append("diskUsable=").append(this.getDiskUsable()).append(",");
        sb.append("memoryMax=").append(this.getMemoryMax()).append(",");
        sb.append("memoryTotal=").append(this.getMemoryTotal()).append(",");
        sb.append("memoryFree=").append(this.getMemoryFree()).append(",");
        sb.append("memoryHeapusage=").append(this.getMemoryHeapusage()).append(",");
        sb.append("memoryNonheapusage=").append(this.getMemoryNonheapusage()).append(",");
        sb.append("osArch=").append(this.getOsArch()).append(",");
        sb.append("osName=").append(this.getOsName()).append(",");
        sb.append("osVersion=").append(this.getOsVersion()).append(",");
        sb.append("osAvailableprocessors=").append(this.getOsAvailableprocessors()).append(",");
        sb.append("osSystemloadaverage=").append(this.getOsSystemloadaverage()).append(",");
        sb.append("osTotalphysicalmemory=").append(this.getOsTotalphysicalmemory()).append(",");
        sb.append("osFreephysicalmemory=").append(this.getOsFreephysicalmemory()).append(",");
        sb.append("osTotalswapspace=").append(this.getOsTotalswapspace()).append(",");
        sb.append("osFreeswapspace=").append(this.getOsFreeswapspace()).append(",");
        sb.append("osProcesstime=").append(this.getOsProcesstime()).append(",");
        sb.append("osCommittedvirtualmemory=").append(this.getOsCommittedvirtualmemory()).append(",");
        sb.append("runtimeJavaversion=").append(this.getRuntimeJavaversion()).append(",");
        sb.append("runtimeStarttime=").append(this.getRuntimeStarttime()).append(",");
        sb.append("runtimeUptime=").append(this.getRuntimeUptime()).append(",");
        sb.append("runtimeUserdir=").append(this.getRuntimeUserdir()).append(",");
        sb.append("runtimeUsername=").append(this.getRuntimeUsername()).append(",");
        sb.append("threadActive=").append(this.getThreadActive()).append(",");
        sb.append("threadCatCount=").append(this.getThreadCatCount()).append(",");
        sb.append("threadCount=").append(this.getThreadCount()).append(",");
        sb.append("threadDaemonCount=").append(this.getThreadDaemonCount()).append(",");
        sb.append("threadHttp=").append(this.getThreadHttp()).append(",");
        sb.append("threadPeek=").append(this.getThreadPeek()).append(",");
        sb.append("threadStarted=").append(this.getThreadStarted()).append(",");
        sb.append("threadTotalStarted=").append(this.getThreadTotalStarted());
        sb.append("]");
        return sb.toString();
    }

}
