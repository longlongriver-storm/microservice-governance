package com.storm.monitor.demo.model.view;

import com.storm.monitor.core.entity.Page;
import com.storm.monitor.core.entity.PageView;
import com.storm.monitor.demo.model.DemoTradeOrder;

/**
 * bean DemoTradeOrderView Title: 【商品订单】的View类 
 * Copyriht: Copyright (c) 2020
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2020-04-23 19:22
 *
 */
public class DemoTradeOrderView extends DemoTradeOrder implements PageView {

    /**
     * 分页导航
     */
    private Page page = new Page();

    /**
     * 交货日期-开始时间
     */
    private java.util.Date deliveryDateBegin;
    /**
     * 交货日期-结束时间
     */
    private java.util.Date deliveryDateEnd;

    public java.util.Date getDeliveryDateBegin() {
        return deliveryDateBegin;
    }

    public void setDeliveryDateBegin(java.util.Date deliveryDateBegin) {
        this.deliveryDateBegin = deliveryDateBegin;
    }

    public java.util.Date getDeliveryDateEnd() {
        return deliveryDateEnd;
    }

    public void setDeliveryDateEnd(java.util.Date deliveryDateEnd) {
        this.deliveryDateEnd = deliveryDateEnd;
    }

    @Override
    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
