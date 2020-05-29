/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.demo.controller;

import com.storm.monitor.demo.common.Response;
import com.storm.monitor.demo.model.DemoTradeOrder;
import com.storm.monitor.demo.model.view.DemoTradeOrderView;
import com.storm.monitor.demo.service.DemoTradeOrderService;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lixin
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private DemoTradeOrderService demoTradeOrderService;

    @RequestMapping
    public String showIndexPage(Map<String, Object> model) {
        return "index";
    }

    /**
     * Spring容器bean监控
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("test1")
    public String showEasyUiMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "test1";
    }

    @RequestMapping({"/testRandom"})
    @ResponseBody
    public Response testRandom() throws Exception {
        Random r=new Random();
        Thread.sleep(r.nextInt(10) + 10);  //模拟业务处理延时
        long cTime=System.currentTimeMillis();
        String eventName="";
        
        if (cTime % 3 == 0) {  // 调用新增接口 
            String[] skuNameArray={null,"电脑","电视","洗衣机",null,null};
            DemoTradeOrder demoTradeOrder = new DemoTradeOrder();
            demoTradeOrder.setSkuName(skuNameArray[r.nextInt(6)]);   //有一半的概率会为null，保存时保持，产生异常事件
            demoTradeOrder.setSkuPrice(Float.valueOf(String.valueOf(r.nextInt(900)+10)));
            demoTradeOrder.setOrderQuantity(r.nextInt(10)+1);
            demoTradeOrder.setDeliveryDate(new Date());
            demoTradeOrder.setCustomerName("系统");
            demoTradeOrder.setDeliveryPlace("北京");
            try {
                demoTradeOrderService.addDemoTradeOrder(demoTradeOrder);
            } catch (Exception ex) {
                return Response.failuer("111111", "调用失败，监控将记录一次异常调用信息,异常信息是：" + ex.getMessage());
            }
            eventName="新增一个订单，订单ID="+demoTradeOrder.getId();
        }else if(cTime % 3==1){    //调用update接口,修改最新一条数据
            DemoTradeOrderView demoTradeOrderView = new DemoTradeOrderView();
            demoTradeOrderView.getPage().setSortColumn("id");
            demoTradeOrderView.getPage().setOrderBy("desc");
            List<DemoTradeOrder> list=demoTradeOrderService.queryDemoTradeOrderByPage(demoTradeOrderView);
            if(list!=null && list.isEmpty()==false){
                DemoTradeOrder demoTradeOrder=list.get(0);
                demoTradeOrder.setOrderDesc("系统自动更新了");
                demoTradeOrderService.updateDemoTradeOrder(demoTradeOrder);
                eventName="修改一个订单，订单ID="+demoTradeOrder.getId();
            }
        }else if(cTime % 3==2){    //调用delete接口,删除一条空数据
            Long id=System.currentTimeMillis()/1000;
            demoTradeOrderService.deleteDemoTradeOrderById(id);
            eventName="删除一个订单，订单ID="+id;
        }

        Response response = Response.success();
        response.setBusinessObject("调用成功，监控将记录一次成功调用事件:"+eventName);
        return response;
    }

}
