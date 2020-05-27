/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.demo.monitor;

import com.storm.monitor.core.util.UUIDGenerator;
import com.storm.monitor.demo.common.Constants;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Random;

/**
 * 在接到一个新的前端请求时，在线程上下文中设置跟踪ID和用户ID
 * @author lixin
 */
public class ControllerMethodInvokedInterceptor implements MethodInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerMethodInvokedInterceptor.class);

    private static final String[] USERS = {"user1", "user2", "user3", "user4", "user5", "user6"};

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object finalResult = null;
        try {
            //赋予TraceId
            Object obj = MDC.get(Constants.TRACER_ID_KEY);
            if (obj == null) {
                MDC.put(Constants.TRACER_ID_KEY, UUIDGenerator.generateUUID32());
            }else{
                LOG.error("已经存在TraceId,traceId={}",obj.toString());
            }
            //模拟获取用户标识，这里就随机取一个固定用户
            obj = MDC.get(Constants.USER_ID_KEY);
            if (obj == null) {
                MDC.put(Constants.USER_ID_KEY, USERS[(new Random()).nextInt(6)]);
            }else{
                LOG.error("已经存在UserKey,UserKey={}",obj.toString());
            }
            //执行真实对象调用
            finalResult=invocation.proceed();
            return finalResult;
        } catch (Exception ex) {
            throw ex;
        }finally{
            //不管成功或者失败，都需要清空MDC的上下文
            MDC.remove(Constants.TRACER_ID_KEY);
            MDC.remove(Constants.USER_ID_KEY);
        }
    }
}
