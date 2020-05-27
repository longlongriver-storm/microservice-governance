/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.demo.monitor;

import com.storm.monitor.demo.common.Constants;
import com.storm.monitor.core.common.BizMessageHandler;
import com.storm.monitor.core.entity.BizMessage;
import com.storm.monitor.core.entity.EventMessage;
import com.storm.monitor.demo.common.Response;
import java.lang.reflect.Method;

import org.slf4j.MDC;

/**
 * 一个自定义业务信息采集器的示例
 * @author lixin
 */
public class DemoBizMessageHandler implements BizMessageHandler{
    
    @Override
    public BizMessage handle(Method method, Object[] arguments, Object finalResult, EventMessage eventMessage) {
        //==============对EventMessage填充上下文跟踪信息===============
        Object obj = MDC.get(Constants.TRACER_ID_KEY);
        if (obj != null) {
            eventMessage.setTraceId((String) obj);
        }
        obj = MDC.get(Constants.USER_ID_KEY);
        if (obj != null) {
            eventMessage.setUserKey((String) obj);
        }
        
        //==============构建业务信息===============
        if (finalResult == null) {
            return null;
        }
        //抓取业务异常
        if (finalResult instanceof Response) {
            Response resp = (Response) finalResult;
            if (!resp.isSuccess()) {
                BizMessage bm = new BizMessage();
                bm.setBizErrCode(resp.getResultCode());
                bm.setBizErrMsg(resp.getResultCodeMsg());
                return bm;
            }
        }
        return null;
    }
}
