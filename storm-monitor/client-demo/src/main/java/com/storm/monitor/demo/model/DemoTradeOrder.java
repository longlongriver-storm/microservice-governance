package com.storm.monitor.demo.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * bean DemoTradeOrder Title: 【商品订单】的PoJo类，也就是数据库表demo_trade_order的映射类 
 * Copyright (c) 2020
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2020-04-23 19:22
 *
 */
public class DemoTradeOrder implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 订单ID
     */
    private java.lang.Long id;
    /**
     * 商品名称
     */
    private java.lang.String skuName;
    /**
     * 商品单价
     */
    private java.lang.Float skuPrice;
    /**
     * 下单数量
     */
    private java.lang.Integer orderQuantity;
    /**
     * 下单客户
     */
    private java.lang.String customerName;
    /**
     * 交货日期
     */
    private java.util.Date deliveryDate;
    /**
     * 交货地点
     */
    private java.lang.String deliveryPlace;
    /**
     * 备注
     */
    private java.lang.String orderDesc;
    /**
     * 订单金额
     */
    private Float orderMoney;

    public java.lang.Long getId() {
        return id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.lang.String getSkuName() {
        return skuName;
    }

    public void setSkuName(java.lang.String skuName) {
        this.skuName = skuName;
    }

    public java.lang.Float getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(java.lang.Float skuPrice) {
        this.skuPrice = skuPrice;
    }

    public java.lang.Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(java.lang.Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public java.lang.String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(java.lang.String customerName) {
        this.customerName = customerName;
    }

    public java.util.Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(java.util.Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public java.lang.String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(java.lang.String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public java.lang.String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(java.lang.String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public Float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Float orderMoney) {
        this.orderMoney = orderMoney;
    }

    /**
     * 复制所有属性到指定对象
     *
     * @param targetDemoTradeOrder 属性赋值目标对象
     */
    public void copyPropertiesTo(DemoTradeOrder targetDemoTradeOrder) {
        if (targetDemoTradeOrder == null) {
            return;
        }
        targetDemoTradeOrder.setId(this.getId());
        targetDemoTradeOrder.setSkuName(this.getSkuName());
        targetDemoTradeOrder.setSkuPrice(this.getSkuPrice());
        targetDemoTradeOrder.setOrderQuantity(this.getOrderQuantity());
        targetDemoTradeOrder.setCustomerName(this.getCustomerName());
        targetDemoTradeOrder.setDeliveryDate(this.getDeliveryDate());
        targetDemoTradeOrder.setDeliveryPlace(this.getDeliveryPlace());
        targetDemoTradeOrder.setOrderDesc(this.getOrderDesc());
    }

    public boolean equals(Object object) {
        if (!(object instanceof DemoTradeOrder)) {
            return false;
        }
        DemoTradeOrder demoTradeOrder2 = (DemoTradeOrder) object;
        return new EqualsBuilder()
                .appendSuper(super.equals(object))
                .append(this.id, demoTradeOrder2.id)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(-528253723, -475504089)
                .appendSuper(super.hashCode())
                .append(this.id)
                .toHashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append("id=").append(this.getId()).append(",");
        sb.append("skuName=").append(this.getSkuName()).append(",");
        sb.append("skuPrice=").append(this.getSkuPrice()).append(",");
        sb.append("orderQuantity=").append(this.getOrderQuantity()).append(",");
        sb.append("customerName=").append(this.getCustomerName()).append(",");
        sb.append("deliveryDate=").append(this.getDeliveryDate()).append(",");
        sb.append("deliveryPlace=").append(this.getDeliveryPlace()).append(",");
        sb.append("orderDesc=").append(this.getOrderDesc());
        sb.append("]");
        return sb.toString();
    }

}
