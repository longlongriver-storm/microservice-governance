package com.storm.monitor.demo.controller;

import com.storm.monitor.demo.service.DemoTradeOrderService;
import com.storm.monitor.demo.model.DemoTradeOrder;
import com.storm.monitor.demo.model.view.DemoTradeOrderView;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/trade_demotradeorder")
public class DemoTradeOrderController {

    @Autowired
    private DemoTradeOrderService demoTradeOrderService;

    /**
     * 跳转到【商品订单】信息列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping("gotodemotradeorder")
    public String gotoDemoTradeOrder(HttpServletRequest request) {
        return "trade/demotradeorder_list";
    }

    /**
     * 查询列表数据
     *
     * @param demoTradeOrderView
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/querydemotradeorder"})
    @ResponseBody
    public List<DemoTradeOrder> queryDemoTradeOrder(DemoTradeOrderView demoTradeOrderView, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<DemoTradeOrder> list = new ArrayList();
        if (demoTradeOrderView != null) {
            list = this.demoTradeOrderService.queryDemoTradeOrderByPage(demoTradeOrderView);
        }

        return list;
    }

    /**
     * 查询列表数据
     *
     * @param demoTradeOrderView
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/querydemotradeorderpage"})
    @ResponseBody
    public Map queryDemoTradeOrderPage(DemoTradeOrderView demoTradeOrderView, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<DemoTradeOrder> list = queryDemoTradeOrder(demoTradeOrderView, request, response);
        int resultCount = (demoTradeOrderView == null) ? 0 : this.demoTradeOrderService.queryDemoTradeOrderByCount(demoTradeOrderView);

        Map result = new HashMap();
        result.put("rows", list);
        result.put("total", resultCount);
        return result;
    }

    /**
     * 打开【商品订单】信息编辑（新增）页面
     *
     * @param demoTradeOrder
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/gotodemotradeorderedit")
    public String gotoDemoTradeOrderEdit(DemoTradeOrder demoTradeOrder, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = demoTradeOrder.getId();
        if (id != null && !"".equals(id)) {
            demoTradeOrder = this.demoTradeOrderService.queryDemoTradeOrderById(id);

        }
        request.setAttribute("pj", demoTradeOrder);

        return "trade/demotradeorder_add";
    }

    /**
     * 新增或者删除一条记录
     *
     * @param demoTradeOrder
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/adddemotradeorder"})
    @ResponseBody
    public DemoTradeOrder addDemoTradeOrder(DemoTradeOrder demoTradeOrder, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //System.out.println(JSON.toJSONString(demoTradeOrder));
        DemoTradeOrder oldCfg = null;

        if (null != demoTradeOrder.getId()) {
            oldCfg = this.demoTradeOrderService.queryDemoTradeOrderById(demoTradeOrder.getId());
        }

        if (oldCfg == null) {   //新增
            this.demoTradeOrderService.addDemoTradeOrder(demoTradeOrder);
        } else {              //更新
            this.demoTradeOrderService.updateDemoTradeOrder(demoTradeOrder);
        }
        
        return demoTradeOrder;
    }

    /**
     * 删除配置
     *
     * @param demoTradeOrder
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping({"/deletedemotradeorder"})
    @ResponseBody
    public boolean deleteDemoTradeOrder(DemoTradeOrder demoTradeOrder, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //System.out.println(JSON.toJSONString(demoTradeOrder));
        Long id = demoTradeOrder.getId();
        //System.out.println(id);
        int count = this.demoTradeOrderService.deleteDemoTradeOrderById(id);

        return count > 0;

    }

}
