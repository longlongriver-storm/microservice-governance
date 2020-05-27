package com.storm.monitor.server.dao;

import com.storm.monitor.server.model.CustomDataPickLog;
import com.storm.monitor.server.model.view.CustomDataPickLogView;

import java.util.List;
import com.storm.monitor.core.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 * Title: 【业务数据采集汇总表】(对应POJO对象：CustomDataPickLog)DAO接口类
 * Description: 业务数据采集汇总表Dao接口类
 * Copyriht: Copyright (c) 2020
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2018-09-27 11:00
 *
 */
public interface CustomDataPickLogMapper extends BaseMapper {

    /**
     * 新增一条记录
     *
     * @param customDataPickLog 待插入的【CustomDataPickLog】实体
     * @return insert操作影响的数据库条数
     * @throws Exception
     */
    public int addCustomDataPickLog(CustomDataPickLog customDataPickLog) throws Exception;

    /**
     * 新增一批记录
     *
     * @param customDataPickLogs 待插入的【CustomDataPickLog】实体集合
     * @return insert操作影响的数据库条数
     * @throws Exception
     */
    public int addCustomDataPickLogBatch(List<CustomDataPickLog> customDataPickLogs) throws Exception;

    /**
     * 更新一条数据库记录（全部字段）
     *
     * @param customDataPickLog 待更新的【CustomDataPickLog】实体
     * @return update操作影响的数据库条数
     * @throws Exception
     */
    public int updateCustomDataPickLog(CustomDataPickLog customDataPickLog) throws Exception;

    /**
     * 更新一条数据库记录（部分字段）
     *
     * @param customDataPickLog 待更新的【CustomDataPickLog】实体
     * @return update操作影响的数据库条数
     * @throws Exception
     */
    public int updateCustomDataPickLogBySelective(CustomDataPickLog customDataPickLog);

    /**
     * 删除一条数据库记录
     *
     * @param customDataPickLog 待删除的【CustomDataPickLog】实体
     * @return delete操作影响的数据库条数
     * @throws Exception
     */
    public int deleteCustomDataPickLogById(java.util.Date logTime) throws Exception;

    /**
     * 根据条件删除数据库记录
     *
     * @param customDataPickLogView
     * @return
     * @throws Exception
     */
    public int deleteCustomDataPickLogByCondition(CustomDataPickLogView customDataPickLogView) throws Exception;

    /**
     * 返回查询操作的总数
     *
     * @param customDataPickLog customDataPickLogView 查询操作的条件
     * @return 符合查询条件的记录总数
     * @throws Exception
     */
    public int queryCustomDataPickLogByCount(CustomDataPickLogView customDataPickLogView) throws Exception;

    /**
     * 返回查询操作的结果
     *
     * @param customDataPickLog customDataPickLogView 查询操作的条件
     * @return 符合查询条件的记录
     * @throws Exception
     */
    public List<CustomDataPickLog> queryCustomDataPickLogByPage(CustomDataPickLogView customDataPickLogView) throws Exception;
    
    /**
     * 根据SQL查询
     * @param sql
     * @return
     * @throws Exception 
     */
    public List<CustomDataPickLog> queryCustomDataPickLogBySQL(@Param("sql") String sql) throws Exception;

    /**
     * 根据主键查询记录
     *
     * @param id 主键ID
     * @return 一条数据库记录
     * @throws Exception
     */
    public CustomDataPickLog queryCustomDataPickLogById(Object id) throws Exception;

}
