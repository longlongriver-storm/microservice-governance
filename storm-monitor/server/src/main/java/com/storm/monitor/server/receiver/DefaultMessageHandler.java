/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.server.receiver;

import com.storm.monitor.core.entity.MessageTree;
import com.storm.monitor.core.server.MessageConsumer;
import com.storm.monitor.core.server.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author lixin
 */
public class DefaultMessageHandler implements MessageHandler{
    private static Logger m_logger=LoggerFactory.getLogger(DefaultMessageHandler.class);
    
    /**
     * 消息消费者接口，目前只有一个实例：RealtimeConsumer
     */
    private MessageConsumer m_consumer;

    public DefaultMessageHandler() {

    }

    /**
     * 处理消息
     *
     * @param tree
     */
    @Override
    public void handle(MessageTree tree) {
        if (m_consumer == null) {
            m_consumer = new RealtimeConsumer();
        }

        try {
            m_consumer.consume(tree);
        } catch (Throwable e) {
            m_logger.error("Error when consuming message in {}! tree: {},error:{}",m_consumer,tree, e);
        }
    }
    
    /**
     * 获取消息处理相关信息
     * @return 
     */
    @Override
    public String getInfo(){
        return m_consumer==null?"<table>No Message!</table>":m_consumer.getInfo();
    }
}
