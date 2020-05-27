/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.server;

import com.storm.monitor.core.entity.MessageTree;

/**
 * 消息消费者接口，目前只有一个实例：RealtimeConsumer
 *
 * @author Administrator
 */
public interface MessageConsumer {

    /**
     * 消息消费
     *
     * @param tree
     */
    public void consume(MessageTree tree);

    /**
     * 系统关闭的时候，进行检测
     */
    public void doCheckpoint();

    /**
     * 获取消息消费情况
     * @return
     */
    public String getInfo();
}
