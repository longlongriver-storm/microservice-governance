package com.storm.monitor.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.storm.monitor.server.dao.DaoMonitorLogDayMapper;
import com.storm.monitor.server.service.DaoMonitorLogDayService;
import com.storm.monitor.server.model.DaoMonitorLogDay;
import com.storm.monitor.server.model.view.DaoMonitorLogDayView;
import java.util.Date;
import java.util.List;

/**
 * Title: 【DAO监控天汇总表】(对应POJO对象：DaoMonitorLogDay)服务实现类
 * Description: 包含对【DAO监控天汇总表】(DaoMonitorLogDay)的增、删、改、查、构建等基本操作
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
@Service
@Transactional
public class DaoMonitorLogDayServiceImpl implements DaoMonitorLogDayService {
    @Autowired
    private DaoMonitorLogDayMapper daoMonitorLogDayMapper;

    /**
    * 新增一条记录
    * @param daoMonitorLogDay 待插入的【DaoMonitorLogDay】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int addDaoMonitorLogDay(DaoMonitorLogDay daoMonitorLogDay)  throws Exception{
        return daoMonitorLogDayMapper.addDaoMonitorLogDay(daoMonitorLogDay);
    }
    
    @Override
    public int addDaoMonitorLogDayBySelect(Date logTime,Date logBeginTime,Date logEndTime)  throws Exception{
        return daoMonitorLogDayMapper.addDaoMonitorLogDayBySelect(logTime, logBeginTime, logEndTime);
    }
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param daoMonitorLogDay 待更新的【DaoMonitorLogDay】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateDaoMonitorLogDay(DaoMonitorLogDay daoMonitorLogDay)  throws Exception{
        return daoMonitorLogDayMapper.updateDaoMonitorLogDay(daoMonitorLogDay);
    }
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param daoMonitorLogDay 待更新的【DaoMonitorLogDay】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateDaoMonitorLogDayBySelective(DaoMonitorLogDay daoMonitorLogDay){
        return daoMonitorLogDayMapper.updateDaoMonitorLogDayBySelective(daoMonitorLogDay);
    }

    /**
    * 删除一条数据库记录
    * @param daoMonitorLogDay 待删除的【DaoMonitorLogDay】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int deleteDaoMonitorLogDayById(java.util.Date  logTime) throws Exception{
        return daoMonitorLogDayMapper.deleteDaoMonitorLogDayById(logTime);
    }

    /**
     * 根据条件删除数据库记录
     *
     * @param daoMonitorLogDayView
     * @return
     * @throws Exception
     */
    public int deleteDaoMonitorLogDayByCondition(DaoMonitorLogDayView daoMonitorLogDayView) throws Exception{
        return daoMonitorLogDayMapper.deleteDaoMonitorLogDayByCondition(daoMonitorLogDayView);
    }
	
    /**
    * 返回查询操作的结果
    * @param daoMonitorLogDay daoMonitorLogDayView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    @Override
    public List<DaoMonitorLogDay> queryDaoMonitorLogDayByPage(DaoMonitorLogDayView daoMonitorLogDayView) throws Exception{
        return daoMonitorLogDayMapper.queryDaoMonitorLogDayByPage(daoMonitorLogDayView);
        
    }
    
    @Override
    public List<DaoMonitorLogDay> queryDaoMonitorLogDayBySQL(String sql) throws Exception{
        return daoMonitorLogDayMapper.queryDaoMonitorLogDayBySQL(sql);
        
    }

     /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    @Override
    public DaoMonitorLogDay queryDaoMonitorLogDayById(Object id) throws Exception{
        return daoMonitorLogDayMapper.queryDaoMonitorLogDayById(id);
    }
}
