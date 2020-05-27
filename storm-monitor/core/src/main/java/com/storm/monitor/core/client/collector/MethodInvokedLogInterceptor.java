package com.storm.monitor.core.client.collector;

import com.storm.monitor.core.client.OnlineCrawlerConfig;
import com.storm.monitor.core.client.Honeycomb;
import com.storm.monitor.core.client.Bee;
import com.storm.monitor.core.util.BeanUtils;
import com.storm.monitor.core.common.Common;
import com.storm.monitor.core.common.BizMessageHandler;
import com.storm.monitor.core.entity.BeanCrawlerConfig;
import com.storm.monitor.core.entity.BizMessage;
import com.storm.monitor.core.entity.EventMessage;
import com.storm.monitor.core.entity.PropertyCrawlerConfig;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.springframework.beans.factory.InitializingBean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Spring方法调用监控
 *
 * @author lixin
 */
public class MethodInvokedLogInterceptor implements MethodInterceptor, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(MethodInvokedLogInterceptor.class);

    /**
     * 业务事件处理器,可以在实际业务系统注入
     */
    BizMessageHandler bizMessageHandler;

    /**
     * 在线数据抓取配置管理
     */
    OnlineCrawlerConfig onlineCrawlerConfig;
    /**
     * APM通用配置
     */
    HashMap<String, String> apmCommonConfig;

    /**
     * Bean初始化的时候，协助进行APM发送器（netty服务）的初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (apmCommonConfig != null) {
            Common.LogStoreMode = apmCommonConfig.get(Common.SPRING_KEY_CLIENT_CONFIG_LOG_STORE_MODE);
            Common.ServerAddress = apmCommonConfig.get(Common.SPRING_KEY_CLIENT_CONFIG_SERVER_ADDRESS);
        }
        LOG.info("**********************************************************************");
        LOG.info("                 Common.LogStoreMode=" + Common.LogStoreMode);
        LOG.info("                 Common.ServerAddress=" + Common.ServerAddress);
        LOG.info("**********************************************************************");
        Honeycomb.getIstance().initSender();
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        Object processor = invocation.getThis();
        String clzName = processor.getClass().getName();
        if (clzName.startsWith("com.sun.proxy") || clzName.startsWith("$Proxy")) {
            for (Class clz : processor.getClass().getInterfaces()) {
                if (clz != null && clz.getName().startsWith("com.storm")) {
                    clzName = clz.getName();
                    break;
                }
            }
        }

        Object finalResult = null;
        EventMessage eventMessage = null;
        try {
            long t1 = System.currentTimeMillis();
            finalResult = invocation.proceed();
            //**********************************************
            //            记录正常调用事件
            //**********************************************
            eventMessage = new EventMessage(clzName, method.getName(), EventMessage.TYPE_SERVICE_METHOD, System.currentTimeMillis() - t1);
        } catch (Exception e) {
            //记录异常调用事件
            eventMessage = new EventMessage(clzName, method.getName(), EventMessage.TYPE_SERVICE_METHOD, null);
            eventMessage.LogError(e);
            throw e;// 异常接着往上抛
        } finally {
            //**********************************************
            //   业务数据及用户userKey及traceId抓取
            //**********************************************
            if (bizMessageHandler != null) {
                //判断是否有业务信息(前提是必须要有业务处理器)
                //但不能因为业务处理器的错误影响了实际业务，所以这里要屏蔽业务事件处理器的错误抛出
                try {
                    BizMessage bm = bizMessageHandler.handle(method, arguments, finalResult, eventMessage);
                    if (bm != null) {
                        eventMessage.setBizMessage(bm);
                    }
                } catch (Exception ex) {
                    LOG.error("log biz message error,class={},method={},Error Message:{}", clzName, eventMessage.getMethodName(), ex);
                }
            }

            //**********************************************
            //   不能因为数据抓取影响正常业务
            //**********************************************
            if (onlineCrawlerConfig != null) {  //数据抓取启动
                String apiKey = clzName + "." + method.getName();

                BeanCrawlerConfig bccf = onlineCrawlerConfig.getApiCrawlerConfig(apiKey);
                if (bccf != null) {   //对本接口定义了抓取
                    for (PropertyCrawlerConfig pccf : bccf.getPropertyCrawlerConfigs()) {
                        try {
                            if (PropertyCrawlerConfig.PROPERTY_TYPE_INPUT.equals(pccf.getPropertyType())) {             //入参抓取
                                eventMessage.addInfo(pccf.getPropertyKey(), BeanUtils.getPropertyValue(arguments[pccf.getInputIndex()], pccf.getPropertyName()));
                            } else if (PropertyCrawlerConfig.PROPERTY_TYPE_OUTPUT.equals(pccf.getPropertyType())) {      //出参抓取(注意整个出参抓取的情况)
                                if (PropertyCrawlerConfig.PROPERTY_NAME_OUTPUT.equals(pccf.getPropertyName())) {   //抓取整个出参
                                    eventMessage.addInfo(pccf.getPropertyKey(), finalResult);
                                } else {    //抓取出参中的属性
                                    eventMessage.addInfo(pccf.getPropertyKey(), BeanUtils.getPropertyValue(finalResult, pccf.getPropertyName()));
                                }

                            }
                        } catch (Exception ex) {
                            //异常不提示
                            //ex.printStackTrace();
                        }

                    }
                }
            }

            Bee.pickEventMessage(eventMessage);
        }

        return finalResult;
    }

    public BizMessageHandler getBizMessageHandler() {
        return bizMessageHandler;
    }

    public void setBizMessageHandler(BizMessageHandler bizMessageHandler) {
        this.bizMessageHandler = bizMessageHandler;
    }

    public OnlineCrawlerConfig getOnlineCrawlerConfig() {
        return onlineCrawlerConfig;
    }

    public void setOnlineCrawlerConfig(OnlineCrawlerConfig onlineCrawlerConfig) {
        this.onlineCrawlerConfig = onlineCrawlerConfig;
    }

    public HashMap<String, String> getApmCommonConfig() {
        return apmCommonConfig;
    }

    public void setApmCommonConfig(HashMap<String, String> apmCommonConfig) {
        this.apmCommonConfig = apmCommonConfig;
    }

}
