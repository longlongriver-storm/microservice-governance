/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.common;

import io.netty.buffer.ByteBuf;
import com.storm.monitor.core.entity.MessageTree;

/**
 * 消息编码器接口 缺省实现是：PlainTextMessageCodec
 *
 * @author Administrator
 */
public interface MessageCodec {

    /**
     * 对流解码成消息树
     *
     * @param buf
     * @return
     */
    public MessageTree decode(ByteBuf buf);

    /**
     * 将字节流解码为消息树
     *
     * @param buf
     * @param tree
     */
    public void decode(ByteBuf buf, MessageTree tree);

    /**
     * 将消息树写入字节流中
     *
     * @param tree
     * @param buf
     */
    public void encode(MessageTree tree, ByteBuf buf);
}
