package com.storm.monitor.server.service;

import com.storm.monitor.server.model.DaoMonitorLogHour;
import com.storm.monitor.server.model.view.DaoMonitorLogHourView;

import java.util.Date;
import java.util.List;

/**
 *
 * Title: 【DAO监控小时汇总表】(对应POJO对象：DaoMonitorLogHour)服务接口类
 * Description: DAO监控小时汇总表管理接口类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
public interface DaoMonitorLogHourService{
    /**
    * 新增一条记录
    * @param daoMonitorLogHour 待插入的【DaoMonitorLogHour】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    public int addDaoMonitorLogHour(DaoMonitorLogHour daoMonitorLogHour)  throws Exception;
    
    public int addDaoMonitorLogHourBySelect(Date logTime,Date logBeginTime,Date logEndTime)  throws Exception;
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param daoMonitorLogHour 待更新的【DaoMonitorLogHour】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateDaoMonitorLogHour(DaoMonitorLogHour daoMonitorLogHour)  throws Exception;
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param daoMonitorLogHour 待更新的【DaoMonitorLogHour】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateDaoMonitorLogHourBySelective(DaoMonitorLogHour daoMonitorLogHour);

    /**
    * 删除一条数据库记录
    * @param daoMonitorLogHour 待删除的【DaoMonitorLogHour】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    public int deleteDaoMonitorLogHourById(java.util.Date  logTime) throws Exception;
	
    /**
     * 根据条件删除数据库记录
     *
     * @param daoMonitorLogHourView
     * @return
     * @throws Exception
     */
    public int deleteDaoMonitorLogHourByCondition(DaoMonitorLogHourView daoMonitorLogHourView) throws Exception;
    
    /**
    * 返回查询操作的结果
    * @param daoMonitorLogHour daoMonitorLogHourView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    public List<DaoMonitorLogHour> queryDaoMonitorLogHourByPage(DaoMonitorLogHourView daoMonitorLogHourView)throws Exception;
    
    public List<DaoMonitorLogHour> queryDaoMonitorLogHourBySQL(String sql)throws Exception;
	
    /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    public DaoMonitorLogHour queryDaoMonitorLogHourById(Object id) throws Exception;
    
}