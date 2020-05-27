package com.storm.monitor.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.storm.monitor.server.dao.MemoryGcMonitorLogMapper;
import com.storm.monitor.server.service.MemoryGcMonitorLogService;
import com.storm.monitor.server.model.MemoryGcMonitorLog;
import com.storm.monitor.server.model.view.MemoryGcMonitorLogView;
import java.util.List;

/**
 * Title: 【内存GC监控日志表】(对应POJO对象：MemoryGcMonitorLog)服务实现类
 * Description: 包含对【内存GC监控日志表】(MemoryGcMonitorLog)的增、删、改、查、构建等基本操作
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
@Service
@Transactional
public class MemoryGcMonitorLogServiceImpl implements MemoryGcMonitorLogService {
    @Autowired
    private MemoryGcMonitorLogMapper memoryGcMonitorLogMapper;

    /**
    * 新增一条记录
    * @param memoryGcMonitorLog 待插入的【MemoryGcMonitorLog】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int addMemoryGcMonitorLog(MemoryGcMonitorLog memoryGcMonitorLog)  throws Exception{
        return memoryGcMonitorLogMapper.addMemoryGcMonitorLog(memoryGcMonitorLog);
    }
    
    @Override
    public int addMemoryGcMonitorLogBatch(List<MemoryGcMonitorLog> memoryGcMonitorLogs)  throws Exception{
        return memoryGcMonitorLogMapper.addMemoryGcMonitorLogBatch(memoryGcMonitorLogs);
    }
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param memoryGcMonitorLog 待更新的【MemoryGcMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateMemoryGcMonitorLog(MemoryGcMonitorLog memoryGcMonitorLog)  throws Exception{
        return memoryGcMonitorLogMapper.updateMemoryGcMonitorLog(memoryGcMonitorLog);
    }
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param memoryGcMonitorLog 待更新的【MemoryGcMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateMemoryGcMonitorLogBySelective(MemoryGcMonitorLog memoryGcMonitorLog){
        return memoryGcMonitorLogMapper.updateMemoryGcMonitorLogBySelective(memoryGcMonitorLog);
    }

    /**
    * 删除一条数据库记录
    * @param memoryGcMonitorLog 待删除的【MemoryGcMonitorLog】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int deleteMemoryGcMonitorLogById(java.util.Date  logTime) throws Exception{
        return memoryGcMonitorLogMapper.deleteMemoryGcMonitorLogById(logTime);
    }

    /**
     * 根据条件删除数据库记录
     *
     * @param memoryGcMonitorLogView
     * @return
     * @throws Exception
     */
    public int deleteMemoryGcMonitorLogByCondition(MemoryGcMonitorLogView memoryGcMonitorLogView) throws Exception{
        return memoryGcMonitorLogMapper.deleteMemoryGcMonitorLogByCondition(memoryGcMonitorLogView);
    }
	
    /**
    * 返回查询操作的结果
    * @param memoryGcMonitorLog memoryGcMonitorLogView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    @Override
    public List<MemoryGcMonitorLog> queryMemoryGcMonitorLogByPage(MemoryGcMonitorLogView memoryGcMonitorLogView) throws Exception{
        return memoryGcMonitorLogMapper.queryMemoryGcMonitorLogByPage(memoryGcMonitorLogView);
        
    }
    
    /**
     * 根据SQL查询
     * @param sql
     * @return
     * @throws Exception 
     */
    public List<MemoryGcMonitorLog> queryMemoryGcMonitorLogBySQL(String sql) throws Exception{
        return memoryGcMonitorLogMapper.queryMemoryGcMonitorLogBySQL(sql);
    }

     /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    @Override
    public MemoryGcMonitorLog queryMemoryGcMonitorLogById(Object id) throws Exception{
        return memoryGcMonitorLogMapper.queryMemoryGcMonitorLogById(id);
    }
}
