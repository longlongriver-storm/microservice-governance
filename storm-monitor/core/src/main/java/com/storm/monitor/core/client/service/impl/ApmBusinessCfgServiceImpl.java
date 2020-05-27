package com.storm.monitor.core.client.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.storm.monitor.core.client.dao.ApmBusinessCfgMapper;
import com.storm.monitor.core.client.service.ApmBusinessCfgService;
import com.storm.monitor.core.client.model.ApmBusinessCfg;
import com.storm.monitor.core.client.model.view.ApmBusinessCfgView;
import java.util.List;

/**
 *
 * Title: 【apm_business_cfg】(对应POJO对象：ApmBusinessCfg)服务实现类
 * Description: 包含对【apm_business_cfg】(ApmBusinessCfg)的增、删、改、查、构建等基本操作
 * Copyriht: Copyright (c) 2020
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2018-09-20 15:40
 *
 */
@Service
@Transactional
public class ApmBusinessCfgServiceImpl implements ApmBusinessCfgService {
    @Autowired
    private ApmBusinessCfgMapper apmBusinessCfgMapper;

    /**
    * 新增一条记录
    * @param apmBusinessCfg 待插入的【ApmBusinessCfg】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int addApmBusinessCfg(ApmBusinessCfg apmBusinessCfg)  throws Exception{
        return apmBusinessCfgMapper.addApmBusinessCfg(apmBusinessCfg);
    }
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param apmBusinessCfg 待更新的【ApmBusinessCfg】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateApmBusinessCfg(ApmBusinessCfg apmBusinessCfg)  throws Exception{
        return apmBusinessCfgMapper.updateApmBusinessCfg(apmBusinessCfg);
    }
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param apmBusinessCfg 待更新的【ApmBusinessCfg】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateApmBusinessCfgBySelective(ApmBusinessCfg apmBusinessCfg){
        return apmBusinessCfgMapper.updateApmBusinessCfgBySelective(apmBusinessCfg);
    }

    /**
    * 删除一条数据库记录
    * @param apmBusinessCfg 待删除的【ApmBusinessCfg】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int deleteApmBusinessCfgById(java.lang.Integer  id) throws Exception{
        return apmBusinessCfgMapper.deleteApmBusinessCfgById(id);
    }

    /**
     * 根据条件删除数据库记录
     *
     * @param apmBusinessCfgView
     * @return
     * @throws Exception
     */
    public int deleteApmBusinessCfgByCondition(ApmBusinessCfgView apmBusinessCfgView) throws Exception{
        return apmBusinessCfgMapper.deleteApmBusinessCfgByCondition(apmBusinessCfgView);
    }
	
    /**
    * 返回查询操作的结果
    * @param apmBusinessCfg apmBusinessCfgView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    @Override
    public List<ApmBusinessCfg> queryApmBusinessCfgByPage(ApmBusinessCfgView apmBusinessCfgView) throws Exception{
        return apmBusinessCfgMapper.queryApmBusinessCfgByPage(apmBusinessCfgView);
    }
    
    /**
    * 返回符合查询条件的记录总数
    * @param apmBusinessCfgView apmBusinessCfgView 查询操作的条件
    * @return 符合查询条件的记录总数
    * @throws Exception 
    */
    @Override
    public int queryApmBusinessCfgByCount(ApmBusinessCfgView apmBusinessCfgView) throws Exception{
        return apmBusinessCfgMapper.queryApmBusinessCfgByCount(apmBusinessCfgView);
    }

     /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    @Override
    public ApmBusinessCfg queryApmBusinessCfgById(Object id) throws Exception{
        return apmBusinessCfgMapper.queryApmBusinessCfgById(id);
    }
}
