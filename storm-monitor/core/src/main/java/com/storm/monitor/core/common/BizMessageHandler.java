/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.common;

import com.storm.monitor.core.entity.BizMessage;
import com.storm.monitor.core.entity.EventMessage;
import java.lang.reflect.Method;

/**
 *
 * @author lixin
 */
public interface BizMessageHandler {
    /**
     * 处理业务事件
     * @param method 被调用的方法
     * @param arguments 输入参数
     * @param finalResult 处理结果
     * @param eventMessage 事件消息，可以在此被填充一些额外信息
     * @return 
     */
    BizMessage handle(Method method,Object[] arguments,Object finalResult,EventMessage eventMessage);
    
}