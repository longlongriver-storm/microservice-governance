/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.common;

/**
 * 通用配置，都是静态变量
 * @author lixin
 */
public class Common {
    /**
     * 一分钟的毫秒值长
     */
    public static final long ONE_MINUTE_INTERVAL = 1000L * 60L;
    /**
     * 日志存储模式：本地存储
     */
    public static final String LOG_STORE_MODE_LOCAL="local";
    /**
     * 日志存储模式：远程存储（NIO发送）
     */
    public static final String LOG_STORE_MODE_REMOTE="remote";
    
    public static final int ERROR_COUNT = 100;

    public static final int SUCCESS_COUNT = 1000;
    
    /**
     * 客户端：日志存储模式KEY
     */
    public static final String SPRING_KEY_CLIENT_CONFIG_LOG_STORE_MODE="LogStoreMode";
    /**
     * 客户端：服务端地址配置KEY
     */
    public static final String SPRING_KEY_CLIENT_CONFIG_SERVER_ADDRESS="ServerAddress";
    /**
     * 服务端：NIO的服务端监听端口
     */
    public static final String SPRING_KEY_SERVER_CONFIG_LISTEN_PORT="ServerPort";
    /**
     * 服务端：收集服务器是否进行定时统计汇总，有两种：1、true：进行定时统计；2、false：不进行汇总统计
     */
    public static final String SPRING_KEY_SERVER_CONFIG_BUILD_REPORT="BuildReport";
    /**
     * 服务端：收集服务器是否启动消息接收服务，有两种：1、true：进行消息接收；2、false：不进行消息接收
     */
    public static final String SPRING_KEY_SERVER_CONFIG_RECEIVE_MESSAGE="ReceiveMessage";
    /**
     * 服务端：HTTP的服务端监听端口
     */
    public static final String SPRING_KEY_SERVER_CONFIG_HTTP_LISTEN_PORT="ServerHttpPort";
    /**
     * Spring中APM配置Bean的Id
     */
    public static final String SPRING_BEAN_KEY="apmCommonConfig";
    
    public static volatile String ServerAddress;
    
    public static volatile String LogStoreMode;
    
    /**
     * 获得时间对应的整分钟对应的毫秒值
     * @param time
     * @return 
     */
    public static long getCompleteMinuteForTime(long time){
        long m_time=time/ONE_MINUTE_INTERVAL;
        return m_time*ONE_MINUTE_INTERVAL;
    }
}
