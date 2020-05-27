package com.storm.monitor.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.storm.monitor.server.dao.CustomDataPickLogMapper;
import com.storm.monitor.server.service.CustomDataPickLogService;
import com.storm.monitor.server.model.CustomDataPickLog;
import com.storm.monitor.server.model.view.CustomDataPickLogView;
import java.util.List;

/**
 * Title: 【业务数据采集汇总表】(对应POJO对象：CustomDataPickLog)服务实现类
 * Description: 包含对【业务数据采集汇总表】(CustomDataPickLog)的增、删、改、查、构建等基本操作
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2018-09-27 11:00
 *
 */
@Service
@Transactional
public class CustomDataPickLogServiceImpl implements CustomDataPickLogService {

    @Autowired
    private CustomDataPickLogMapper customDataPickLogMapper;

    /**
     * 新增一条记录
     *
     * @param customDataPickLog 待插入的【CustomDataPickLog】实体
     * @return insert操作影响的数据库条数
     * @throws Exception
     */
    @Override
    public int addCustomDataPickLog(CustomDataPickLog customDataPickLog) throws Exception {
        return customDataPickLogMapper.addCustomDataPickLog(customDataPickLog);
    }

    /**
     * 新增一批记录
     *
     * @param customDataPickLogs 待插入的【CustomDataPickLog】实体集合
     * @return insert操作影响的数据库条数
     * @throws Exception
     */
    @Override
    public int addCustomDataPickLogBatch(List<CustomDataPickLog> customDataPickLogs) throws Exception {
        return customDataPickLogMapper.addCustomDataPickLogBatch(customDataPickLogs);
    }

    /**
     * 更新一条数据库记录（全部字段）
     *
     * @param customDataPickLog 待更新的【CustomDataPickLog】实体
     * @return update操作影响的数据库条数
     * @throws Exception
     */
    @Override
    public int updateCustomDataPickLog(CustomDataPickLog customDataPickLog) throws Exception {
        return customDataPickLogMapper.updateCustomDataPickLog(customDataPickLog);
    }

    /**
     * 更新一条数据库记录（部分字段）
     *
     * @param customDataPickLog 待更新的【CustomDataPickLog】实体
     * @return update操作影响的数据库条数
     * @throws Exception
     */
    @Override
    public int updateCustomDataPickLogBySelective(CustomDataPickLog customDataPickLog) throws Exception{
        return customDataPickLogMapper.updateCustomDataPickLogBySelective(customDataPickLog);
    }

    /**
     * 根据条件删除数据库记录
     *
     * @param customDataPickLogView
     * @return
     * @throws Exception
     */
    public int deleteCustomDataPickLogByCondition(CustomDataPickLogView customDataPickLogView) throws Exception {
        return customDataPickLogMapper.deleteCustomDataPickLogByCondition(customDataPickLogView);
    }

    /**
     * 返回查询操作的结果
     *
     * @param customDataPickLog customDataPickLogView 查询操作的条件
     * @return 符合查询条件的记录
     * @throws Exception
     */
    @Override
    public List<CustomDataPickLog> queryCustomDataPickLogByPage(CustomDataPickLogView customDataPickLogView) throws Exception {
        return customDataPickLogMapper.queryCustomDataPickLogByPage(customDataPickLogView);
    }

    /**
     * 返回符合查询条件的记录总数
     *
     * @param customDataPickLogView customDataPickLogView 查询操作的条件
     * @return 符合查询条件的记录总数
     * @throws Exception
     */
    @Override
    public int queryCustomDataPickLogByCount(CustomDataPickLogView customDataPickLogView) throws Exception {
        return customDataPickLogMapper.queryCustomDataPickLogByCount(customDataPickLogView);
    }

    /**
     * 根据SQL查询
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public List<CustomDataPickLog> queryCustomDataPickLogBySQL(String sql) throws Exception {
        return customDataPickLogMapper.queryCustomDataPickLogBySQL(sql);
    }

    /**
     * 根据主键查询记录
     *
     * @param id 主键ID
     * @return 一条数据库记录
     * @throws Exception
     */
    @Override
    public CustomDataPickLog queryCustomDataPickLogById(Object id) throws Exception {
        return customDataPickLogMapper.queryCustomDataPickLogById(id);
    }
}
