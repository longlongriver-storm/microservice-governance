package com.storm.monitor.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.storm.monitor.server.dao.ServiceMonitorLogHourMapper;
import com.storm.monitor.server.service.ServiceMonitorLogHourService;
import com.storm.monitor.server.model.ServiceMonitorLogHour;
import com.storm.monitor.server.model.view.ServiceMonitorLogHourView;
import java.util.Date;
import java.util.List;

/**
 * Title: 【服务监控小时汇总表】(对应POJO对象：ServiceMonitorLogHour)服务实现类
 * Description: 包含对【服务监控小时汇总表】(ServiceMonitorLogHour)的增、删、改、查、构建等基本操作
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
@Service
@Transactional
public class ServiceMonitorLogHourServiceImpl implements ServiceMonitorLogHourService {
    @Autowired
    private ServiceMonitorLogHourMapper serviceMonitorLogHourMapper;

    /**
    * 新增一条记录
    * @param serviceMonitorLogHour 待插入的【ServiceMonitorLogHour】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int addServiceMonitorLogHour(ServiceMonitorLogHour serviceMonitorLogHour)  throws Exception{
        return serviceMonitorLogHourMapper.addServiceMonitorLogHour(serviceMonitorLogHour);
    }
    
    public int addServiceMonitorLogHourBySelect(Date logTime,Date logBeginTime,Date logEndTime)  throws Exception{
        return serviceMonitorLogHourMapper.addServiceMonitorLogHourBySelect(logTime, logBeginTime, logEndTime);
    }
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param serviceMonitorLogHour 待更新的【ServiceMonitorLogHour】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateServiceMonitorLogHour(ServiceMonitorLogHour serviceMonitorLogHour)  throws Exception{
        return serviceMonitorLogHourMapper.updateServiceMonitorLogHour(serviceMonitorLogHour);
    }
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param serviceMonitorLogHour 待更新的【ServiceMonitorLogHour】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateServiceMonitorLogHourBySelective(ServiceMonitorLogHour serviceMonitorLogHour){
        return serviceMonitorLogHourMapper.updateServiceMonitorLogHourBySelective(serviceMonitorLogHour);
    }

    /**
    * 删除一条数据库记录
    * @param serviceMonitorLogHour 待删除的【ServiceMonitorLogHour】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int deleteServiceMonitorLogHourById(java.util.Date  logTime) throws Exception{
        return serviceMonitorLogHourMapper.deleteServiceMonitorLogHourById(logTime);
    }

    /**
     * 根据条件删除数据库记录
     *
     * @param serviceMonitorLogHourView
     * @return
     * @throws Exception
     */
    public int deleteServiceMonitorLogHourByCondition(ServiceMonitorLogHourView serviceMonitorLogHourView) throws Exception{
        return serviceMonitorLogHourMapper.deleteServiceMonitorLogHourByCondition(serviceMonitorLogHourView);
    }
	
    /**
    * 返回查询操作的结果
    * @param serviceMonitorLogHour serviceMonitorLogHourView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    @Override
    public List<ServiceMonitorLogHour> queryServiceMonitorLogHourByPage(ServiceMonitorLogHourView serviceMonitorLogHourView) throws Exception{
        return serviceMonitorLogHourMapper.queryServiceMonitorLogHourByPage(serviceMonitorLogHourView);
        
    }
    
    @Override
    public List<ServiceMonitorLogHour> queryServiceMonitorLogHourBySQL(String sql) throws Exception{
        return serviceMonitorLogHourMapper.queryServiceMonitorLogHourBySQL(sql);
        
    }

     /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    @Override
    public ServiceMonitorLogHour queryServiceMonitorLogHourById(Object id) throws Exception{
        return serviceMonitorLogHourMapper.queryServiceMonitorLogHourById(id);
    }
}
