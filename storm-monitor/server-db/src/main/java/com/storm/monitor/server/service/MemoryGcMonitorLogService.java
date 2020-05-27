package com.storm.monitor.server.service;

import com.storm.monitor.server.model.MemoryGcMonitorLog;
import com.storm.monitor.server.model.view.MemoryGcMonitorLogView;

import java.util.List;

/**
 *
 * Title: 【内存GC监控日志表】(对应POJO对象：MemoryGcMonitorLog)服务接口类
 * Description: 内存GC监控日志表管理接口类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
public interface MemoryGcMonitorLogService{
    /**
    * 新增一条记录
    * @param memoryGcMonitorLog 待插入的【MemoryGcMonitorLog】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    public int addMemoryGcMonitorLog(MemoryGcMonitorLog memoryGcMonitorLog)  throws Exception;
    
    public int addMemoryGcMonitorLogBatch(List<MemoryGcMonitorLog> memoryGcMonitorLogs)  throws Exception;
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param memoryGcMonitorLog 待更新的【MemoryGcMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateMemoryGcMonitorLog(MemoryGcMonitorLog memoryGcMonitorLog)  throws Exception;
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param memoryGcMonitorLog 待更新的【MemoryGcMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateMemoryGcMonitorLogBySelective(MemoryGcMonitorLog memoryGcMonitorLog);

    /**
    * 删除一条数据库记录
    * @param memoryGcMonitorLog 待删除的【MemoryGcMonitorLog】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    public int deleteMemoryGcMonitorLogById(java.util.Date  logTime) throws Exception;
	
    /**
     * 根据条件删除数据库记录
     *
     * @param memoryGcMonitorLogView
     * @return
     * @throws Exception
     */
    public int deleteMemoryGcMonitorLogByCondition(MemoryGcMonitorLogView memoryGcMonitorLogView) throws Exception;
    
    /**
    * 返回查询操作的结果
    * @param memoryGcMonitorLog memoryGcMonitorLogView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    public List<MemoryGcMonitorLog> queryMemoryGcMonitorLogByPage(MemoryGcMonitorLogView memoryGcMonitorLogView)throws Exception;
	
    /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    public MemoryGcMonitorLog queryMemoryGcMonitorLogById(Object id) throws Exception;
    
    public List<MemoryGcMonitorLog> queryMemoryGcMonitorLogBySQL(String sql) throws Exception;
    
}