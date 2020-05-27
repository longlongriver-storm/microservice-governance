package com.storm.monitor.server.service;

import com.storm.monitor.server.model.DiskVolumeMonitorLog;
import com.storm.monitor.server.model.view.DiskVolumeMonitorLogView;

import java.util.List;

/**
 *
 * Title: 【磁盘监控日志表】(对应POJO对象：DiskVolumeMonitorLog)服务接口类
 * Description: 磁盘监控日志表管理接口类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
public interface DiskVolumeMonitorLogService{
    /**
    * 新增一条记录
    * @param diskVolumeMonitorLog 待插入的【DiskVolumeMonitorLog】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    public int addDiskVolumeMonitorLog(DiskVolumeMonitorLog diskVolumeMonitorLog)  throws Exception;
    
    public int addDiskVolumeMonitorLogBatch(List<DiskVolumeMonitorLog> diskVolumeMonitorLogs)  throws Exception;
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param diskVolumeMonitorLog 待更新的【DiskVolumeMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateDiskVolumeMonitorLog(DiskVolumeMonitorLog diskVolumeMonitorLog)  throws Exception;
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param diskVolumeMonitorLog 待更新的【DiskVolumeMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateDiskVolumeMonitorLogBySelective(DiskVolumeMonitorLog diskVolumeMonitorLog);

    /**
    * 删除一条数据库记录
    * @param diskVolumeMonitorLog 待删除的【DiskVolumeMonitorLog】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    public int deleteDiskVolumeMonitorLogById(java.util.Date  logTime) throws Exception;
	
    /**
     * 根据条件删除数据库记录
     *
     * @param diskVolumeMonitorLogView
     * @return
     * @throws Exception
     */
    public int deleteDiskVolumeMonitorLogByCondition(DiskVolumeMonitorLogView diskVolumeMonitorLogView) throws Exception;
    
    /**
    * 返回查询操作的结果
    * @param diskVolumeMonitorLog diskVolumeMonitorLogView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    public List<DiskVolumeMonitorLog> queryDiskVolumeMonitorLogByPage(DiskVolumeMonitorLogView diskVolumeMonitorLogView)throws Exception;
	
    /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    public DiskVolumeMonitorLog queryDiskVolumeMonitorLogById(Object id) throws Exception;
    
}