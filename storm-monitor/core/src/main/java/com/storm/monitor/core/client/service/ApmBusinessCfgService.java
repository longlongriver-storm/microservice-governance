package com.storm.monitor.core.client.service;

import com.storm.monitor.core.client.model.ApmBusinessCfg;
import com.storm.monitor.core.client.model.view.ApmBusinessCfgView;

import java.util.List;

/**
 *
 * Title: 【apm_business_cfg】(对应POJO对象：ApmBusinessCfg)服务接口类
 * Copyriht: Copyright (c) 2020
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2018-09-20 15:40
 *
 */
public interface ApmBusinessCfgService{
    /**
    * 新增一条记录
    * @param apmBusinessCfg 待插入的【ApmBusinessCfg】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    public int addApmBusinessCfg(ApmBusinessCfg apmBusinessCfg)  throws Exception;
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param apmBusinessCfg 待更新的【ApmBusinessCfg】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateApmBusinessCfg(ApmBusinessCfg apmBusinessCfg)  throws Exception;
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param apmBusinessCfg 待更新的【ApmBusinessCfg】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateApmBusinessCfgBySelective(ApmBusinessCfg apmBusinessCfg);

    /**
    * 删除一条数据库记录
    * @param apmBusinessCfg 待删除的【ApmBusinessCfg】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    public int deleteApmBusinessCfgById(java.lang.Integer  id) throws Exception;
	
    /**
     * 根据条件删除数据库记录
     *
     * @param apmBusinessCfgView
     * @return
     * @throws Exception
     */
    public int deleteApmBusinessCfgByCondition(ApmBusinessCfgView apmBusinessCfgView) throws Exception;
    
    /**
    * 返回查询操作的结果
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    public List<ApmBusinessCfg> queryApmBusinessCfgByPage(ApmBusinessCfgView apmBusinessCfgView)throws Exception;
    
    /**
    * 返回查询操作的总数
    * @param apmBusinessCfgView apmBusinessCfgView 查询操作的条件
    * @return 符合查询条件的记录总数
    * @throws Exception 
    */
    public int queryApmBusinessCfgByCount(ApmBusinessCfgView apmBusinessCfgView)throws Exception;
	
    /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    public ApmBusinessCfg queryApmBusinessCfgById(Object id) throws Exception;
    
}