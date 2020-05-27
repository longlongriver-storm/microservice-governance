package com.storm.monitor.dashboard.service;

import com.storm.monitor.dashboard.model.ApmChartConfg;
import com.storm.monitor.dashboard.model.view.ApmChartConfgView;

import java.util.List;

/**
 *
 * Title: 【图表配置】(对应POJO对象：ApmChartConfg)服务接口类
 * Copyriht: Copyright (c) 2020
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2018-10-19 09:45
 *
 */
public interface ApmChartConfgService{
    /**
    * 新增一条记录
    * @param apmChartConfg 待插入的【ApmChartConfg】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    public int addApmChartConfg(ApmChartConfg apmChartConfg)  throws Exception;

    /**
    * 批量增加记录
    * @param apmChartConfgs 待插入的【ApmChartConfg】实体集合
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    public int addApmChartConfgBatch(List<ApmChartConfg> apmChartConfgs)  throws Exception;
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param apmChartConfg 待更新的【ApmChartConfg】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateApmChartConfg(ApmChartConfg apmChartConfg)  throws Exception;
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param apmChartConfg 待更新的【ApmChartConfg】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateApmChartConfgBySelective(ApmChartConfg apmChartConfg) throws Exception;

    /**
    * 删除一条数据库记录
    * @param apmChartConfg 待删除的【ApmChartConfg】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    public int deleteApmChartConfgById(java.lang.String  id) throws Exception;
	
    /**
     * 根据条件删除数据库记录
     *
     * @param apmChartConfgView
     * @return
     * @throws Exception
     */
    public int deleteApmChartConfgByCondition(ApmChartConfgView apmChartConfgView) throws Exception;
    
    /**
    * 返回查询操作的结果
    * @param apmChartConfg apmChartConfgView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    public List<ApmChartConfg> queryApmChartConfgByPage(ApmChartConfgView apmChartConfgView)throws Exception;
    
    /**
    * 返回查询操作的总数
    * @param apmChartConfgView apmChartConfgView 查询操作的条件
    * @return 符合查询条件的记录总数
    * @throws Exception 
    */
    public int queryApmChartConfgByCount(ApmChartConfgView apmChartConfgView)throws Exception;
	
    /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    public ApmChartConfg queryApmChartConfgById(Object id) throws Exception;
    
}