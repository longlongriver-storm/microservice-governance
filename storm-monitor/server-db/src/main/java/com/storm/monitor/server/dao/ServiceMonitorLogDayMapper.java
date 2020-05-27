package com.storm.monitor.server.dao;

import com.storm.monitor.server.model.ServiceMonitorLogDay;
import com.storm.monitor.server.model.view.ServiceMonitorLogDayView;

import java.util.List;
import com.storm.monitor.core.common.BaseMapper;
import java.util.Date;
import org.apache.ibatis.annotations.Param;

/**
 *
 * Title: 【服务监控天汇总表】(对应POJO对象：ServiceMonitorLogDay)DAO接口类
 * Description: 服务监控天汇总表Dao接口类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
public interface ServiceMonitorLogDayMapper extends BaseMapper{
        /**
        * 新增一条记录
        * @param serviceMonitorLogDay 待插入的【ServiceMonitorLogDay】实体
        * @return insert操作影响的数据库条数
        * @throws Exception 
        */
	public int addServiceMonitorLogDay(ServiceMonitorLogDay serviceMonitorLogDay)  throws Exception;
    
    public int addServiceMonitorLogDayBySelect(@Param("logTime") Date logTime,@Param("logBeginTime") Date logBeginTime,@Param("logEndTime") Date logEndTime)  throws Exception;
	
        /**
        * 更新一条数据库记录（全部字段）
        * @param serviceMonitorLogDay 待更新的【ServiceMonitorLogDay】实体
        * @return update操作影响的数据库条数
        * @throws Exception 
        */
	public int updateServiceMonitorLogDay(ServiceMonitorLogDay serviceMonitorLogDay)  throws Exception;
	
        /**
        * 更新一条数据库记录（部分字段）
        * @param serviceMonitorLogDay 待更新的【ServiceMonitorLogDay】实体
        * @return update操作影响的数据库条数
        * @throws Exception 
        */
	public int updateServiceMonitorLogDayBySelective(ServiceMonitorLogDay serviceMonitorLogDay);

        /**
        * 删除一条数据库记录
        * @param serviceMonitorLogDay 待删除的【ServiceMonitorLogDay】实体
        * @return delete操作影响的数据库条数
        * @throws Exception 
        */
        public int deleteServiceMonitorLogDayById(java.util.Date  logTime) throws Exception;
	
        /**
         * 根据条件删除数据库记录
         * @param serviceMonitorLogDayView
         * @return
         * @throws Exception 
         */
        public int deleteServiceMonitorLogDayByCondition(ServiceMonitorLogDayView serviceMonitorLogDayView) throws Exception;

        /**
        * 返回查询操作的总数
        * @param serviceMonitorLogDay serviceMonitorLogDayView 查询操作的条件
        * @return 符合查询条件的记录总数
        * @throws Exception 
        */
	public int queryServiceMonitorLogDayByCount(ServiceMonitorLogDayView serviceMonitorLogDayView)throws Exception;
	
        /**
        * 返回查询操作的结果
        * @param serviceMonitorLogDay serviceMonitorLogDayView 查询操作的条件
        * @return 符合查询条件的记录
        * @throws Exception 
        */
	public List<ServiceMonitorLogDay> queryServiceMonitorLogDayByPage(ServiceMonitorLogDayView serviceMonitorLogDayView) throws Exception;
    
    public List<ServiceMonitorLogDay> queryServiceMonitorLogDayBySQL(@Param("sql") String sql) throws Exception;

        /**
        * 根据主键查询记录
        * @param id  主键ID
        * @return 一条数据库记录
        * @throws Exception 
        */
	public ServiceMonitorLogDay queryServiceMonitorLogDayById(Object id) throws Exception;

    
}