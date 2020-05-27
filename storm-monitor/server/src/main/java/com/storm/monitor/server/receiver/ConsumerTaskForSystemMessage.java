/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.server.receiver;

import com.alibaba.fastjson.JSON;
import com.storm.monitor.server.model.DiskVolumeMonitorLog;
import com.storm.monitor.server.model.MemoryGcMonitorLog;
import com.storm.monitor.server.model.SystemMonitorLog;
import com.storm.monitor.server.service.DiskVolumeMonitorLogService;
import com.storm.monitor.server.service.MemoryGcMonitorLogService;
import com.storm.monitor.server.service.SystemMonitorLogService;
import com.storm.monitor.core.util.ApplicationContextUtil;
import com.storm.monitor.core.entity.MessageTree;
import com.storm.monitor.core.entity.SystemStatusInfo;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统监控消息处理线程，直接入库操作
 */
public class ConsumerTaskForSystemMessage extends Thread {

    private static Logger LOG = LoggerFactory.getLogger(ConsumerTaskForSystemMessage.class);

    private BlockingQueue<MessageTree> systemMonitorQueue;
    
    private volatile long allSaveCount = 0;    //已成功保存的消息数量
    private volatile long errorSaveCount = 0;   //未能保存的消息数量

    public ConsumerTaskForSystemMessage(BlockingQueue<MessageTree> systemMonitorQueue) {
        this.systemMonitorQueue = systemMonitorQueue;
    }

    @Override
    public void run() {
        SystemMonitorLogService systemMonitorLogService = null;
        DiskVolumeMonitorLogService diskVolumeMonitorLogService = null;
        MemoryGcMonitorLogService memoryGcMonitorLogService = null;
        
        int nullIdx = 0;               //连续从消息队列中获取null值（无值）的次数，连续5次获取到null值，则让线程睡眠5毫秒
        while (true) {
            try {
                //从消息队列中获取一个消息
                MessageTree event = systemMonitorQueue.poll();

                int saveCount = 0;
                if (event != null) {
                    String str = event.getMessage();
                    int idx = str.indexOf(",SystemStatusInfo=") + ",SystemStatusInfo=".length();
                    str = str.substring(idx, str.length() - 1);
                    SystemStatusInfo status = JSON.parseObject(str, SystemStatusInfo.class);
                    SystemMonitorLog serviceMonitorLog = SystemMonitorLog.buildSystemMonitorLog(event, status);
                    List<DiskVolumeMonitorLog> vols = DiskVolumeMonitorLog.buildDiskVolumeMonitorLogs(serviceMonitorLog, status);
                    List<MemoryGcMonitorLog> gcs = MemoryGcMonitorLog.buildMemoryGcMonitorLogs(serviceMonitorLog, status);
                    if (systemMonitorLogService == null) {
                        systemMonitorLogService = ApplicationContextUtil.getBean(SystemMonitorLogService.class);
                        diskVolumeMonitorLogService = ApplicationContextUtil.getBean(DiskVolumeMonitorLogService.class);
                        memoryGcMonitorLogService = ApplicationContextUtil.getBean(MemoryGcMonitorLogService.class);
                    }
                    //批量保存一次日志数据
                    saveCount = systemMonitorLogService.addSystemMonitorLog(serviceMonitorLog);
                    saveCount += diskVolumeMonitorLogService.addDiskVolumeMonitorLogBatch(vols);
                    saveCount += memoryGcMonitorLogService.addMemoryGcMonitorLogBatch(gcs);
                    if (saveCount > 0) {  //保存成功
                        allSaveCount += saveCount;
                    }
                }

                if (saveCount > 0 && (allSaveCount % 1000L < 50L)) {
                    LOG.info("ConsumerTaskForSystemMessage,all save success message count is {},"
                        + "error save message count is {}", allSaveCount, errorSaveCount);
                }

                if (event == null && saveCount == 0) {   //消息队列为空，并且没有保存入库操作
                    nullIdx++;
                    if (nullIdx >= 5) {
                        nullIdx = 0;
                        Thread.sleep(50);  //连续5次取不到消息，则线程睡眠5毫秒
                    }
                } else {
                    event = null;
                    nullIdx = 0;
                }
            } catch (Exception e) {
                LOG.error("ConsumerTaskForSystemMessage is error,{}", e.getMessage());
            }
        }

    }

    public long getAllSaveCount() {
        return allSaveCount;
    }

    public long getErrorSaveCount() {
        return errorSaveCount;
    }
    
    
}
