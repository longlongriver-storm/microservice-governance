/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务消息，包含业务错误信息等等
 *
 * @author lixin
 */
public class BizMessage {

    /**
     * 业务错误代码
     */
    String bizErrCode;
    /**
     * 业务错误消息
     */
    String bizErrMsg;
    /**
     * 附加业务信息,键值对
     */
    Map<String, Object> bizInfos;

    public BizMessage() {
    }

    public String getBizErrCode() {
        return bizErrCode;
    }

    public void setBizErrCode(String bizErrCode) {
        this.bizErrCode = bizErrCode;
    }

    public String getBizErrMsg() {
        return bizErrMsg;
    }

    public void setBizErrMsg(String bizErrMsg) {
        this.bizErrMsg = bizErrMsg;
    }

    public void addBizInfo(String infoKey, Object infoValue) {
        if (bizInfos == null) {
            bizInfos = new HashMap();
        }
        bizInfos.put(infoKey, infoValue);
    }

    public Map<String, Object> getBizInfos() {
        return bizInfos;
    }
}
