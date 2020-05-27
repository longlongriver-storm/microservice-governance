package com.storm.monitor.demo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.storm.monitor.demo.dao.DemoTradeOrderMapper;
import com.storm.monitor.demo.service.DemoTradeOrderService;
import com.storm.monitor.demo.model.DemoTradeOrder;
import com.storm.monitor.demo.model.view.DemoTradeOrderView;
import java.util.List;

/**
 *
 * Title: 【商品订单】(对应POJO对象：DemoTradeOrder)服务实现类
 * Copyriht: Copyright (c) 2020
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2020-04-23 19:22
 *
 */
@Service
@Transactional
public class DemoTradeOrderServiceImpl implements DemoTradeOrderService {
    @Autowired
    private DemoTradeOrderMapper demoTradeOrderMapper;

    /**
    * 新增一条记录
    * @param demoTradeOrder 待插入的【DemoTradeOrder】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public DemoTradeOrder addDemoTradeOrder(DemoTradeOrder demoTradeOrder)  throws Exception{
        int rownum=demoTradeOrderMapper.addDemoTradeOrder(demoTradeOrder);
        demoTradeOrder.setOrderMoney(demoTradeOrder.getSkuPrice()*demoTradeOrder.getOrderQuantity());
        return demoTradeOrder;
    }

    /**
    * 新增一批记录
    * @param demoTradeOrders 待插入的【DemoTradeOrder】实体集合
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int addDemoTradeOrderBatch(List<DemoTradeOrder> demoTradeOrders)  throws Exception{
        return demoTradeOrderMapper.addDemoTradeOrderBatch(demoTradeOrders);
    }
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param demoTradeOrder 待更新的【DemoTradeOrder】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public DemoTradeOrder updateDemoTradeOrder(DemoTradeOrder demoTradeOrder)  throws Exception{
         int rownum=demoTradeOrderMapper.updateDemoTradeOrder(demoTradeOrder);
         demoTradeOrder.setOrderMoney(demoTradeOrder.getSkuPrice()*demoTradeOrder.getOrderQuantity());
         return demoTradeOrder;
    }
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param demoTradeOrder 待更新的【DemoTradeOrder】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateDemoTradeOrderBySelective(DemoTradeOrder demoTradeOrder){
        return demoTradeOrderMapper.updateDemoTradeOrderBySelective(demoTradeOrder);
    }

    /**
    * 删除一条数据库记录
    * @param demoTradeOrder 待删除的【DemoTradeOrder】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int deleteDemoTradeOrderById(java.lang.Long  id) throws Exception{
        return demoTradeOrderMapper.deleteDemoTradeOrderById(id);
    }

    /**
     * 根据条件删除数据库记录
     *
     * @param demoTradeOrderView
     * @return
     * @throws Exception
     */
    public int deleteDemoTradeOrderByCondition(DemoTradeOrderView demoTradeOrderView) throws Exception{
        return demoTradeOrderMapper.deleteDemoTradeOrderByCondition(demoTradeOrderView);
    }
	
    /**
    * 返回查询操作的结果
    * @param demoTradeOrderView demoTradeOrderView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    @Override
    public List<DemoTradeOrder> queryDemoTradeOrderByPage(DemoTradeOrderView demoTradeOrderView) throws Exception{
        return demoTradeOrderMapper.queryDemoTradeOrderByPage(demoTradeOrderView);
        
    }

    /**
    * 返回符合查询条件的记录总数
    * @param demoTradeOrderView demoTradeOrderView 查询操作的条件
    * @return 符合查询条件的记录总数
    * @throws Exception 
    */
    @Override
    public int queryDemoTradeOrderByCount(DemoTradeOrderView demoTradeOrderView) throws Exception{
        return demoTradeOrderMapper.queryDemoTradeOrderByCount(demoTradeOrderView);

    }

     /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    @Override
    public DemoTradeOrder queryDemoTradeOrderById(Object id) throws Exception{
        return demoTradeOrderMapper.queryDemoTradeOrderById(id);
    }
}
