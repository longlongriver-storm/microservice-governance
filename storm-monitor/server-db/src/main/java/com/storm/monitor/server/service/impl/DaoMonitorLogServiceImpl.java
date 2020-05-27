package com.storm.monitor.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.storm.monitor.server.dao.DaoMonitorLogMapper;
import com.storm.monitor.server.service.DaoMonitorLogService;
import com.storm.monitor.server.model.DaoMonitorLog;
import com.storm.monitor.server.model.view.DaoMonitorLogView;
import java.util.List;

/**
 * Title: 【DAO监控日志表】(对应POJO对象：DaoMonitorLog)服务实现类
 * Description: 包含对【DAO监控日志表】(DaoMonitorLog)的增、删、改、查、构建等基本操作
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-10 14:59
 *
 */
@Service
@Transactional
public class DaoMonitorLogServiceImpl implements DaoMonitorLogService {
    @Autowired
    private DaoMonitorLogMapper daoMonitorLogMapper;

    /**
    * 新增一条记录
    * @param daoMonitorLog 待插入的【DaoMonitorLog】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int addDaoMonitorLog(DaoMonitorLog daoMonitorLog)  throws Exception{
        return daoMonitorLogMapper.addDaoMonitorLog(daoMonitorLog);
    }
    
    /**
     * 批量添加数据
     * @param daoMonitorLogs
     * @return
     * @throws Exception 
     */
    @Override
    public int addDaoMonitorLogBatch(List<DaoMonitorLog> daoMonitorLogs) throws Exception{
        return daoMonitorLogMapper.addDaoMonitorLogBatch(daoMonitorLogs);
    }
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param daoMonitorLog 待更新的【DaoMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateDaoMonitorLog(DaoMonitorLog daoMonitorLog)  throws Exception{
        return daoMonitorLogMapper.updateDaoMonitorLog(daoMonitorLog);
    }
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param daoMonitorLog 待更新的【DaoMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateDaoMonitorLogBySelective(DaoMonitorLog daoMonitorLog){
        return daoMonitorLogMapper.updateDaoMonitorLogBySelective(daoMonitorLog);
    }

    /**
    * 删除一条数据库记录
    * @param daoMonitorLog 待删除的【DaoMonitorLog】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int deleteDaoMonitorLogById(java.util.Date  logTime) throws Exception{
        return daoMonitorLogMapper.deleteDaoMonitorLogById(logTime);
    }

    /**
     * 根据条件删除数据库记录
     *
     * @param daoMonitorLogView
     * @return
     * @throws Exception
     */
    public int deleteDaoMonitorLogByCondition(DaoMonitorLogView daoMonitorLogView) throws Exception{
        return daoMonitorLogMapper.deleteDaoMonitorLogByCondition(daoMonitorLogView);
    }
	
    /**
    * 返回查询操作的结果
    * @param daoMonitorLog daoMonitorLogView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    @Override
    public List<DaoMonitorLog> queryDaoMonitorLogByPage(DaoMonitorLogView daoMonitorLogView) throws Exception{
        return daoMonitorLogMapper.queryDaoMonitorLogByPage(daoMonitorLogView);
        
    }
    
    @Override
    public List<DaoMonitorLog> queryDaoMonitorLogBySQL(String sql) throws Exception{
        return daoMonitorLogMapper.queryDaoMonitorLogBySQL(sql);
        
    }

     /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    @Override
    public DaoMonitorLog queryDaoMonitorLogById(Object id) throws Exception{
        return daoMonitorLogMapper.queryDaoMonitorLogById(id);
    }
}
