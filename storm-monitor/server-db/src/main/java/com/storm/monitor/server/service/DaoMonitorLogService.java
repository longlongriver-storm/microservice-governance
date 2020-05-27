package com.storm.monitor.server.service;

import com.storm.monitor.server.model.DaoMonitorLog;
import com.storm.monitor.server.model.view.DaoMonitorLogView;

import java.util.List;

/**
 * Title: 【DAO监控日志表】(对应POJO对象：DaoMonitorLog)服务接口类
 * Description: DAO监控日志表管理接口类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-10 14:59
 *
 */
public interface DaoMonitorLogService{
    /**
    * 新增一条记录
    * @param daoMonitorLog 待插入的【DaoMonitorLog】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    public int addDaoMonitorLog(DaoMonitorLog daoMonitorLog)  throws Exception;
    
    /**
     * 批量添加数据
     * @param daoMonitorLogs
     * @return
     * @throws Exception 
     */
    public int addDaoMonitorLogBatch(List<DaoMonitorLog> daoMonitorLogs) throws Exception;
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param daoMonitorLog 待更新的【DaoMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateDaoMonitorLog(DaoMonitorLog daoMonitorLog)  throws Exception;
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param daoMonitorLog 待更新的【DaoMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateDaoMonitorLogBySelective(DaoMonitorLog daoMonitorLog);

    /**
    * 删除一条数据库记录
    * @param daoMonitorLog 待删除的【DaoMonitorLog】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    public int deleteDaoMonitorLogById(java.util.Date  logTime) throws Exception;
	
    /**
     * 根据条件删除数据库记录
     *
     * @param daoMonitorLogView
     * @return
     * @throws Exception
     */
    public int deleteDaoMonitorLogByCondition(DaoMonitorLogView daoMonitorLogView) throws Exception;
    
    /**
    * 返回查询操作的结果
    * @param daoMonitorLog daoMonitorLogView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    public List<DaoMonitorLog> queryDaoMonitorLogByPage(DaoMonitorLogView daoMonitorLogView)throws Exception;
    
    public List<DaoMonitorLog> queryDaoMonitorLogBySQL(String sql)throws Exception;
	
    /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    public DaoMonitorLog queryDaoMonitorLogById(Object id) throws Exception;
    
}