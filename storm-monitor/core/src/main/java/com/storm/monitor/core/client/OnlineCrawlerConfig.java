/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.client;

import com.storm.monitor.core.common.NamedThreadFactory;

import org.apache.commons.lang.StringUtils;
import com.storm.monitor.core.client.model.ApmBusinessCfg;
import com.storm.monitor.core.client.model.view.ApmBusinessCfgView;
import com.storm.monitor.core.client.service.ApmBusinessCfgService;
import com.storm.monitor.core.entity.BeanCrawlerConfig;
import com.storm.monitor.core.entity.PropertyCrawlerConfig;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * APM中的在线数据抓取配置
 *
 * @author lixin
 */
public class OnlineCrawlerConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    ApmBusinessCfgService apmBusinessCfgService;

    /**
     * Bean抓取定义
     */
    private volatile Map<String, BeanCrawlerConfig> onlineBeanCrawlerConfig = new ConcurrentHashMap();

    /**
     * 定时检查mock文件的变化及其所在文件夹的变化
     */
    private ScheduledExecutorService scheduledThreadPool;

    public BeanCrawlerConfig getApiCrawlerConfig(String apiKey) {
        return onlineBeanCrawlerConfig.get(apiKey);
    }

    //@Override
    public void init() throws Exception {
        //初始查询错误不能影响后续定时查询操作
        try {
            loadCrawlerConfig();       //这里可能会因为myBatis还没有初始化报错
        } catch (Exception ex) {
            log.error("OnlineCrawlerConfig.init error,error message={}", ex);
        }
        //启动mock文件监控
        //启动定时线程池
        scheduledThreadPool = Executors.newScheduledThreadPool(1, new NamedThreadFactory("apm-reload-crawler-config-schedule", true));
        scheduledThreadPool.scheduleWithFixedDelay(new ReloadApmCrawlerConfigMonitor(), 1000L * 15L, 1000L * 60L, TimeUnit.MILLISECONDS);
    }

    private class ReloadApmCrawlerConfigMonitor implements Runnable {

        @Override
        public void run() {
            try {
                loadCrawlerConfig();
            } catch (Exception ex) {
                log.error("ReloadApmCrawlerConfigMonitor.run error,msg=", ex);
            }
        }
    }

    /**
     * 重新加载抓取配置(双向比对)
     */
    private void loadCrawlerConfig() throws Exception {
        //Thread.sleep(1000L * 10L);
        ApmBusinessCfgView apmBusinessCfgView = new ApmBusinessCfgView();
        apmBusinessCfgView.getPage().setBegin(0);
        apmBusinessCfgView.getPage().setEnd(10000);
        List<ApmBusinessCfg> allCfgs = apmBusinessCfgService.queryApmBusinessCfgByPage(apmBusinessCfgView);
        Set<String> keys = new HashSet();
        //****正向比对，增加或者替换*****
        for (ApmBusinessCfg cfg : allCfgs) {
            keys.add(cfg.getClassMethodName());
            try {
                BeanCrawlerConfig beanCfg = onlineBeanCrawlerConfig.get(cfg.getClassMethodName());
                if (beanCfg != null && beanCfg.getModifyTime().compareTo(cfg.getModifyTime()) == 0) {  //数据没有修改，不必替换
                    continue;
                }
                //创建一个新的抓取定义
                BeanCrawlerConfig newBeanCfg = new BeanCrawlerConfig(cfg.getClassMethodName(), cfg.getModifyTime());
                //处理入参抓取
                if (!StringUtils.isEmpty(cfg.getParameters())) {
                    String[] strArr = cfg.getParameters().split(";");
                    for (String str : strArr) {
                        int idx = str.indexOf(".");
                        PropertyCrawlerConfig propertyCCfg = null;
                        if (idx < 0) {
                            propertyCCfg = new PropertyCrawlerConfig(
                                    Integer.valueOf(str), null);
                        } else {
                            propertyCCfg = new PropertyCrawlerConfig(
                                    Integer.valueOf(str.substring(0, idx)), str.substring(idx + 1));
                        }

                        newBeanCfg.addPropertyCrawlerConfig(propertyCCfg);
                    }
                }
                //处理出参抓取
                if (!StringUtils.isEmpty(cfg.getReturnResult())) {
                    String[] strArr = cfg.getReturnResult().split(";");
                    for (String str : strArr) {
                        PropertyCrawlerConfig propertyCCfg = new PropertyCrawlerConfig(str);
                        newBeanCfg.addPropertyCrawlerConfig(propertyCCfg);
                    }
                }
                //替换抓取
                onlineBeanCrawlerConfig.put(cfg.getClassMethodName(), newBeanCfg);
            } catch (Exception ex) {
                log.error("OnlineCrawlerConfig.loadCrawlerConfig error,error message={}", ex);
            }

        }

        //*****逆向比对，移除*****
        for (String key : onlineBeanCrawlerConfig.keySet()) {
            if (!keys.contains(key)) {
                onlineBeanCrawlerConfig.remove(key);
            }
        }

        //log.info("-------------------------------------OnlineCrawlerConfig.loadCrawlerConfig,load result={}", JSON.toJSONString(onlineBeanCrawlerConfig));
    }
}
