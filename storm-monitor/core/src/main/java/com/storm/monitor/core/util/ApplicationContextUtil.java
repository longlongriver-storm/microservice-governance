/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 用来获取Spring的ApplicationContext，其实在系统的很多地方都实现了ApplicationContextAware接口，都可以获取到context
 *
 * @author lixinjs@dangdang.com
 *
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;
    private static Logger logger = LoggerFactory.getLogger(ApplicationContextUtil.class);

    @Override
    public void setApplicationContext(ApplicationContext context) {
        logger.info("注入ApplicationContext到SpringFactory:" + context);
        if (ApplicationContextUtil.context != null) {
            logger.warn("SpringFactory中的ApplicationContext被覆盖, 原有ApplicationContext为:"
                    + ApplicationContextUtil.context);
        }
        this.context = context;
    }

    public static ApplicationContext getContext() {
        assertContextInjected();
        return context;
    }

    /**
     * 实现DisposableBean接口,在Context关闭时清理静态变量.
     */
    public void destroy() throws Exception {
        ApplicationContextUtil.clear();
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        try{
            return (T) context.getBean(name);
        }catch(org.springframework.beans.factory.NoSuchBeanDefinitionException ex){
            return null;
        }
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * 注意，类型必须完全匹配，不能是接口
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        try{
            T t=context.getBean(requiredType);
            return t;
        }catch(org.springframework.beans.factory.NoSuchBeanDefinitionException ex){
            return null;
        }
        //return context.getBean(requiredType);
    }

    /**
     * 清除SpringFactory中的ApplicationContext为Null.
     */
    private static void clear() {
        logger.debug("清除SpringFactory中的ApplicationContext:" + context);
        context = null;
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        if (ApplicationContextUtil.context == null) {
            /**
             * 当 ApplicationContext 为空时，可能是Spring未初始化完成，先等待一段时间，然后再检查
             * ApplicationContext对象是否为空
             */
            try {
                while (ApplicationContextUtil.context == null) {
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (ApplicationContextUtil.context == null) {
                throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringFactory");
            }
        }
    }
    
    public static DefaultListableBeanFactory getBeanFactory(){
        ApplicationContext springAppContext = ApplicationContextUtil.getContext();
        DefaultListableBeanFactory beanFactory=null;
        if(springAppContext instanceof GenericApplicationContext){
            GenericApplicationContext gac=(GenericApplicationContext)springAppContext;
            beanFactory=gac.getDefaultListableBeanFactory();
        }else if(springAppContext instanceof ClassPathXmlApplicationContext){
            ClassPathXmlApplicationContext gac=(ClassPathXmlApplicationContext)springAppContext;
            beanFactory=(DefaultListableBeanFactory)gac.getBeanFactory();
        }else{
            //在Web环境下，可以将ApplicationContext转换为XmlWebApplicationContext对象
            XmlWebApplicationContext webContext = (XmlWebApplicationContext) springAppContext;
            //DefaultListableBeanFactory 负责在Spring容器启动时初始化所有的Bean定义
            beanFactory = (DefaultListableBeanFactory) webContext.getBeanFactory();
        }
        return beanFactory;
    }

    /**
     * 读取Spring Bean 容器中所有的Bean定义信息
     *
     * @return Map<String, BeanDefinition>
     * @author Mayi
     * @since 2010年3月25日
     */
    public static Map<String, BeanDefinition> getBeanDefinitions() {
        DefaultListableBeanFactory beanFactory=getBeanFactory();

        //读取Spring中定义的所有的Bean名称
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        Map<String, BeanDefinition> beanMap = new HashMap<String, BeanDefinition>();

        for (int i = 0; i < beanNames.length; i++) {
            beanMap.put(beanNames[i], beanFactory.getBeanDefinition(beanNames[i]));
        }
        return beanMap;
    }
}
