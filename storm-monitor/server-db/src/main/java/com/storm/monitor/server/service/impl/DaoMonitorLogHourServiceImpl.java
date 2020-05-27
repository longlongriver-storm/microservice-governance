package com.storm.monitor.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.storm.monitor.server.dao.DaoMonitorLogHourMapper;
import com.storm.monitor.server.service.DaoMonitorLogHourService;
import com.storm.monitor.server.model.DaoMonitorLogHour;
import com.storm.monitor.server.model.view.DaoMonitorLogHourView;
import java.util.Date;
import java.util.List;

/**
 * Title: 【DAO监控小时汇总表】(对应POJO对象：DaoMonitorLogHour)服务实现类
 * Description: 包含对【DAO监控小时汇总表】(DaoMonitorLogHour)的增、删、改、查、构建等基本操作
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
@Service
@Transactional
public class DaoMonitorLogHourServiceImpl implements DaoMonitorLogHourService {
    @Autowired
    private DaoMonitorLogHourMapper daoMonitorLogHourMapper;

    /**
    * 新增一条记录
    * @param daoMonitorLogHour 待插入的【DaoMonitorLogHour】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int addDaoMonitorLogHour(DaoMonitorLogHour daoMonitorLogHour)  throws Exception{
        return daoMonitorLogHourMapper.addDaoMonitorLogHour(daoMonitorLogHour);
    }
    
    public int addDaoMonitorLogHourBySelect(Date logTime,Date logBeginTime,Date logEndTime)  throws Exception{
        return daoMonitorLogHourMapper.addDaoMonitorLogHourBySelect(logTime, logBeginTime, logEndTime);
    }
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param daoMonitorLogHour 待更新的【DaoMonitorLogHour】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateDaoMonitorLogHour(DaoMonitorLogHour daoMonitorLogHour)  throws Exception{
        return daoMonitorLogHourMapper.updateDaoMonitorLogHour(daoMonitorLogHour);
    }
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param daoMonitorLogHour 待更新的【DaoMonitorLogHour】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateDaoMonitorLogHourBySelective(DaoMonitorLogHour daoMonitorLogHour){
        return daoMonitorLogHourMapper.updateDaoMonitorLogHourBySelective(daoMonitorLogHour);
    }

    /**
    * 删除一条数据库记录
    * @param daoMonitorLogHour 待删除的【DaoMonitorLogHour】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int deleteDaoMonitorLogHourById(java.util.Date  logTime) throws Exception{
        return daoMonitorLogHourMapper.deleteDaoMonitorLogHourById(logTime);
    }

    /**
     * 根据条件删除数据库记录
     *
     * @param daoMonitorLogHourView
     * @return
     * @throws Exception
     */
    public int deleteDaoMonitorLogHourByCondition(DaoMonitorLogHourView daoMonitorLogHourView) throws Exception{
        return daoMonitorLogHourMapper.deleteDaoMonitorLogHourByCondition(daoMonitorLogHourView);
    }
	
    /**
    * 返回查询操作的结果
    * @param daoMonitorLogHour daoMonitorLogHourView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    @Override
    public List<DaoMonitorLogHour> queryDaoMonitorLogHourByPage(DaoMonitorLogHourView daoMonitorLogHourView) throws Exception{
        return daoMonitorLogHourMapper.queryDaoMonitorLogHourByPage(daoMonitorLogHourView);
        
    }
    
    @Override
    public List<DaoMonitorLogHour> queryDaoMonitorLogHourBySQL(String sql) throws Exception{
        return daoMonitorLogHourMapper.queryDaoMonitorLogHourBySQL(sql);
        
    }

     /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    @Override
    public DaoMonitorLogHour queryDaoMonitorLogHourById(Object id) throws Exception{
        return daoMonitorLogHourMapper.queryDaoMonitorLogHourById(id);
    }
}
