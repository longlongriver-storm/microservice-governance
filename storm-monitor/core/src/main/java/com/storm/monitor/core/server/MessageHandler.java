/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.server;

import com.storm.monitor.core.entity.MessageTree;

public interface MessageHandler {
    /**
     * 消息处理
     * @param message 
     */
	public void handle(MessageTree message);
    /**
     * 获取消息处理相关信息
     * @return 
     */
    public String getInfo();
}
