/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.demo.controller;

import com.storm.monitor.demo.common.Response;
import com.storm.monitor.demo.model.DemoTradeOrder;
import com.storm.monitor.demo.service.DemoTradeOrderService;
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
        Thread.sleep((new Random()).nextInt(10) + 10);  //模拟业务处理延时
        if (System.currentTimeMillis() % 2 == 0) {   //是偶数   
            Random r=new Random();
            
            DemoTradeOrder demoTradeOrder = new DemoTradeOrder();
            demoTradeOrder.setSkuName("电视机");
            demoTradeOrder.setSkuPrice(Float.valueOf(String.valueOf(r.nextInt(1000)+10)));
            demoTradeOrder.setOrderQuantity(r.nextInt(10)+1);
            try {
                //保存一个空对象，让它故意出错，以验证异常信息采集能力
                demoTradeOrderService.addDemoTradeOrder(demoTradeOrder);
            } catch (Exception ex) {
                return Response.failuer("111111", "调用失败，监控将记录一次异常调用信息,异常信息是：" + ex.getMessage());
            }
        }

        Response response = Response.success();
        response.setBusinessObject("调用成功，监控将记录一次成功调用事件");
        return response;
    }

}
