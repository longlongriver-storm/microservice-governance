package com.storm.monitor.server.service;

import com.storm.monitor.server.model.DaoMonitorLogDay;
import com.storm.monitor.server.model.view.DaoMonitorLogDayView;

import java.util.Date;
import java.util.List;

/**
 *
 * Title: 【DAO监控天汇总表】(对应POJO对象：DaoMonitorLogDay)服务接口类
 * Description: DAO监控天汇总表管理接口类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
public interface DaoMonitorLogDayService{
    /**
    * 新增一条记录
    * @param daoMonitorLogDay 待插入的【DaoMonitorLogDay】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    public int addDaoMonitorLogDay(DaoMonitorLogDay daoMonitorLogDay)  throws Exception;
    
    public int addDaoMonitorLogDayBySelect(Date logTime,Date logBeginTime,Date logEndTime)  throws Exception;
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param daoMonitorLogDay 待更新的【DaoMonitorLogDay】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateDaoMonitorLogDay(DaoMonitorLogDay daoMonitorLogDay)  throws Exception;
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param daoMonitorLogDay 待更新的【DaoMonitorLogDay】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateDaoMonitorLogDayBySelective(DaoMonitorLogDay daoMonitorLogDay);

    /**
    * 删除一条数据库记录
    * @param daoMonitorLogDay 待删除的【DaoMonitorLogDay】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    public int deleteDaoMonitorLogDayById(java.util.Date  logTime) throws Exception;
	
    /**
     * 根据条件删除数据库记录
     *
     * @param daoMonitorLogDayView
     * @return
     * @throws Exception
     */
    public int deleteDaoMonitorLogDayByCondition(DaoMonitorLogDayView daoMonitorLogDayView) throws Exception;
    
    /**
    * 返回查询操作的结果
    * @param daoMonitorLogDay daoMonitorLogDayView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    public List<DaoMonitorLogDay> queryDaoMonitorLogDayByPage(DaoMonitorLogDayView daoMonitorLogDayView)throws Exception;
    
    public List<DaoMonitorLogDay> queryDaoMonitorLogDayBySQL(String sql)throws Exception;
	
    /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    public DaoMonitorLogDay queryDaoMonitorLogDayById(Object id) throws Exception;
    
}