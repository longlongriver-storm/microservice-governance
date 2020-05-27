package com.storm.monitor.demo.service;

import com.storm.monitor.demo.model.DemoTradeOrder;
import com.storm.monitor.demo.model.view.DemoTradeOrderView;

import java.util.List;

/**
 *
 * Title: 【商品订单】(对应POJO对象：DemoTradeOrder)服务接口类
 * Copyriht: Copyright (c) 2020
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2020-04-23 19:22
 *
 */
public interface DemoTradeOrderService{
    /**
    * 新增一条记录
    * @param demoTradeOrder 待插入的【DemoTradeOrder】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    public DemoTradeOrder addDemoTradeOrder(DemoTradeOrder demoTradeOrder)  throws Exception;

    /**
    * 批量增加记录
    * @param demoTradeOrders 待插入的【DemoTradeOrder】实体集合
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    public int addDemoTradeOrderBatch(List<DemoTradeOrder> demoTradeOrders)  throws Exception;
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param demoTradeOrder 待更新的【DemoTradeOrder】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public DemoTradeOrder updateDemoTradeOrder(DemoTradeOrder demoTradeOrder)  throws Exception;
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param demoTradeOrder 待更新的【DemoTradeOrder】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateDemoTradeOrderBySelective(DemoTradeOrder demoTradeOrder);

    /**
    * 删除一条数据库记录
    * @param demoTradeOrder 待删除的【DemoTradeOrder】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    public int deleteDemoTradeOrderById(java.lang.Long  id) throws Exception;
	
    /**
     * 根据条件删除数据库记录
     *
     * @param demoTradeOrderView
     * @return
     * @throws Exception
     */
    public int deleteDemoTradeOrderByCondition(DemoTradeOrderView demoTradeOrderView) throws Exception;
    
    /**
    * 返回查询操作的结果
    * @param demoTradeOrderView demoTradeOrderView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    public List<DemoTradeOrder> queryDemoTradeOrderByPage(DemoTradeOrderView demoTradeOrderView)throws Exception;

    /**
    * 返回查询操作的总数
    * @param demoTradeOrderView demoTradeOrderView 查询操作的条件
    * @return 符合查询条件的记录总数
    * @throws Exception 
    */
    public int queryDemoTradeOrderByCount(DemoTradeOrderView demoTradeOrderView)throws Exception;
	
    /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    public DemoTradeOrder queryDemoTradeOrderById(Object id) throws Exception;
    
}