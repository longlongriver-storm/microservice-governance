package com.storm.monitor.server.model;

import com.storm.monitor.core.entity.MessageTree;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * bean DaoMonitorLog Title: 【DAO监控日志表】的PoJo类，也就是数据库表dao_monitor_log的映射类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-10 14:59
 *
 */
public class DaoMonitorLog extends ServiceMonitorLog implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
    public static DaoMonitorLog buildDaoMonitorLog(MessageTree event) {
        //"{},{},success={},failure={},avgElapsed={},maxElapsed={},minElapsed={},lastestErrorMsg={};"
        DaoMonitorLog daoMonitorLog = new DaoMonitorLog();
        
        String str = event.getMessage();
        int idx = str.indexOf(",");
        daoMonitorLog.setLogTime(new Date(Long.valueOf(str.substring(0, idx))));
        daoMonitorLog.setMachineAddress(event.getIpAddress());
        str = str.substring(idx + 1);  //排除时间

        idx = str.indexOf(",");
        daoMonitorLog.setServiceName(str.substring(0, idx));
        str = str.substring(idx + 1);  //排除服务名称

        idx = str.indexOf(",");
        daoMonitorLog.setSuccessCount(Long.valueOf((str.substring(0, idx).split("="))[1]));
        str = str.substring(idx + 1);  //排除成功数
        
        idx = str.indexOf(",");
        daoMonitorLog.setFailureCount(Long.valueOf((str.substring(0, idx).split("="))[1]));
        str = str.substring(idx + 1); 
        
        idx = str.indexOf(",");
        daoMonitorLog.setAvgElapsed(Long.valueOf((str.substring(0, idx).split("="))[1]));
        str = str.substring(idx + 1);  

        idx = str.indexOf(",");
        daoMonitorLog.setMaxElapsed(Long.valueOf((str.substring(0, idx).split("="))[1]));
        str = str.substring(idx + 1); 

        idx = str.indexOf(",");
        daoMonitorLog.setMinElapsed(Long.valueOf((str.substring(0, idx).split("="))[1]));

        idx = str.indexOf(",lastestErrorMsg=")+",lastestErrorMsg=".length();
        daoMonitorLog.setLastestBizErrorMsg(str.substring(idx, str.length()-1));
        
        return daoMonitorLog;
    }

    /**
     * 复制所有属性到指定对象
     *
     * @param targetDaoMonitorLog 属性赋值目标对象
     */
    public void copyPropertiesTo(DaoMonitorLog targetDaoMonitorLog) {
        if (targetDaoMonitorLog == null) {
            return;
        }
        targetDaoMonitorLog.setLogTime(this.getLogTime());
        targetDaoMonitorLog.setMachineAddress(this.getMachineAddress());
        targetDaoMonitorLog.setServiceName(this.getServiceName());
        targetDaoMonitorLog.setSuccessCount(this.getSuccessCount());
        targetDaoMonitorLog.setFailureCount(this.getFailureCount());
        targetDaoMonitorLog.setAvgElapsed(this.getAvgElapsed());
        targetDaoMonitorLog.setMaxElapsed(this.getMaxElapsed());
        targetDaoMonitorLog.setMinElapsed(this.getMinElapsed());
        targetDaoMonitorLog.setLastestErrorMsg(this.getLastestErrorMsg());
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DaoMonitorLog)) {
            return false;
        }
        DaoMonitorLog daoMonitorLog2 = (DaoMonitorLog) object;
        return new EqualsBuilder()
            .appendSuper(super.equals(object))
            .append(this.getLogTime(), daoMonitorLog2.getLogTime())
            .append(this.getMachineAddress(), daoMonitorLog2.getMachineAddress())
            .append(this.getServiceName(), daoMonitorLog2.getServiceName())
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


}
