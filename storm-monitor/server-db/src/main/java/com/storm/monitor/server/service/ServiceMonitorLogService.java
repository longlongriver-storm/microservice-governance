package com.storm.monitor.server.service;

import com.storm.monitor.server.model.ServiceMonitorLog;
import com.storm.monitor.server.model.view.ServiceMonitorLogView;

import java.util.List;

/**
 * Title: 【服务监控日志表】(对应POJO对象：ServiceMonitorLog)服务接口类
 * Description: 服务监控日志表管理接口类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-10 14:59
 *
 */
public interface ServiceMonitorLogService{
    /**
    * 新增一条记录
    * @param serviceMonitorLog 待插入的【ServiceMonitorLog】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    public int addServiceMonitorLog(ServiceMonitorLog serviceMonitorLog)  throws Exception;
    
    /**
     * 批量增加记录
     * @param serviceMonitorLogs
     * @return
     * @throws Exception 
     */
    public int addServiceMonitorLogBatch(List<ServiceMonitorLog> serviceMonitorLogs)  throws Exception;
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param serviceMonitorLog 待更新的【ServiceMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateServiceMonitorLog(ServiceMonitorLog serviceMonitorLog)  throws Exception;
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param serviceMonitorLog 待更新的【ServiceMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateServiceMonitorLogBySelective(ServiceMonitorLog serviceMonitorLog);
    
    public int updateServiceMonitorLogName(java.util.Date logBeginTime,java.util.Date logEndTime,String newServiceName,String oldServiceName)  throws Exception;

    /**
    * 删除一条数据库记录
    * @param serviceMonitorLog 待删除的【ServiceMonitorLog】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    public int deleteServiceMonitorLogById(java.util.Date  logTime) throws Exception;
	
    /**
     * 根据条件删除数据库记录
     *
     * @param serviceMonitorLogView
     * @return
     * @throws Exception
     */
    public int deleteServiceMonitorLogByCondition(ServiceMonitorLogView serviceMonitorLogView) throws Exception;
    
    /**
    * 返回查询操作的结果
    * @param serviceMonitorLog serviceMonitorLogView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    public List<ServiceMonitorLog> queryServiceMonitorLogByPage(ServiceMonitorLogView serviceMonitorLogView)throws Exception;
    
    /**
     * 根据SQL查询
     * @param sql
     * @return
     * @throws Exception 
     */
    public List<ServiceMonitorLog> queryServiceMonitorLogBySQL(String sql) throws Exception;
	
    /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    public ServiceMonitorLog queryServiceMonitorLogById(Object id) throws Exception;
    
}