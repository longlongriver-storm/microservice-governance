package com.storm.monitor.server.service;

import com.storm.monitor.server.model.SystemMonitorLog;
import com.storm.monitor.server.model.view.SystemMonitorLogView;

import java.util.List;

/**
 * Title: 【系统监控日志表】(对应POJO对象：SystemMonitorLog)服务接口类
 * Description: 系统监控日志表管理接口类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
public interface SystemMonitorLogService{
    /**
    * 新增一条记录
    * @param systemMonitorLog 待插入的【SystemMonitorLog】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    public int addSystemMonitorLog(SystemMonitorLog systemMonitorLog)  throws Exception;
    
    /**
     * 新增一条记录
     *
     * @param systemMonitorLog 待插入的【SystemMonitorLog】实体
     * @return insert操作影响的数据库条数
     * @throws Exception
     */
    public int addSystemMonitorLogBatch(List<SystemMonitorLog> systemMonitorLog) throws Exception;
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param systemMonitorLog 待更新的【SystemMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateSystemMonitorLog(SystemMonitorLog systemMonitorLog)  throws Exception;
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param systemMonitorLog 待更新的【SystemMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateSystemMonitorLogBySelective(SystemMonitorLog systemMonitorLog);

    /**
    * 删除一条数据库记录
    * @param systemMonitorLog 待删除的【SystemMonitorLog】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    public int deleteSystemMonitorLogById(java.util.Date  logTime) throws Exception;
	
    /**
     * 根据条件删除数据库记录
     *
     * @param systemMonitorLogView
     * @return
     * @throws Exception
     */
    public int deleteSystemMonitorLogByCondition(SystemMonitorLogView systemMonitorLogView) throws Exception;
    
    /**
    * 返回查询操作的结果
    * @param systemMonitorLog systemMonitorLogView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    public List<SystemMonitorLog> querySystemMonitorLogByPage(SystemMonitorLogView systemMonitorLogView)throws Exception;
    
    public List<SystemMonitorLog> querySystemMonitorLogBySQL(String sql) throws Exception;
	
    /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    public SystemMonitorLog querySystemMonitorLogById(Object id) throws Exception;
    
}