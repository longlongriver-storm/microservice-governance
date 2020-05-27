/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Bean属性抓取定义器
 *
 * @author lixin
 */
public class BeanCrawlerConfig {

    /**
     * 接口名称，构成格式：className.methodName
     */
    String apiName;
    /**
     * 更新时间
     */
    Date modifyTime;
    /**
     * 要抓取的属性配置
     */
    List<PropertyCrawlerConfig> propertyCrawlerConfigs = new ArrayList();

    public BeanCrawlerConfig(String apiName, Date modifyTime) {
        this.apiName = apiName;
        this.modifyTime = modifyTime;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public List<PropertyCrawlerConfig> getPropertyCrawlerConfigs() {
        return propertyCrawlerConfigs;
    }

    public void setPropertyCrawlerConfigs(List<PropertyCrawlerConfig> propertyCrawlerConfigs) {
        this.propertyCrawlerConfigs = propertyCrawlerConfigs;
    }

    public boolean addPropertyCrawlerConfig(PropertyCrawlerConfig propertyCrawlerConfig) {
        return propertyCrawlerConfigs.add(propertyCrawlerConfig);
    }

    public void resetPropertyCrawlerConfigs() {
        propertyCrawlerConfigs.clear();
    }
}
