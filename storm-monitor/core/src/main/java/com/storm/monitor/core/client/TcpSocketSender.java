/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.client;

import com.storm.monitor.core.common.MessageCodec;
import com.storm.monitor.core.PlainTextMessageCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.storm.monitor.core.entity.MessageTree;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 消息发送器
 *
 * @author lixin
 */
public class TcpSocketSender {

    private static final Logger LOG = LoggerFactory.getLogger(TcpSocketSender.class);

    /**
     * 通道不可用的次数
     */
    private AtomicInteger m_attempts = new AtomicInteger();

    private AtomicInteger m_errors = new AtomicInteger();

    /**
     * 每10秒钟会检测一次服务端对外服务配置信息是否变化（获取服务端的路由信息）； 若配置信息有变化，重新创建与服务端的通信通道
     * 通过HTTP协议获取服务端针对本客户机的配置信息，服务端被访问的IP地址是：ip地址格式：<strong>http://serverIP:serverPort/cat/s/router?domain=clientDomain&ip=clientIp</strong>
     * 一旦有新的服务端地址信息，则建立和新的服务端的NIO连接通道，并关闭旧的通道。
     */
    private ChannelManager m_manager;

    private MessageCodec m_codec = new PlainTextMessageCodec();

    /**
     * 发送消息队列大小
     */
    public static final int SIZE = 5000;

    /**
     * 发送的消息队列
     */
    private BlockingQueue<MessageTree> m_queue = new LinkedBlockingQueue<MessageTree>(SIZE);

    /**
     * 由于消息队列满了，导致无法被发送的消息数量
     */
    private long m_overflowed;

    /**
     * 发送消息的总大小
     */
    private long m_bytes;
    /**
     * 发送消息个数
     */
    private long m_produced;

    private volatile static TcpSocketSender tcpSocketSender;

    private TcpSocketSender() {
        initialize();
    }

    public static TcpSocketSender getInstance() {
        if (tcpSocketSender == null) {
            synchronized (TcpSocketSender.class) {
                if (tcpSocketSender == null) {
                    tcpSocketSender = new TcpSocketSender();
                    //tcpSocketSender.initialize();   //进行初始化，运行进程(这里不调用初始化方法，初始化方法在DefaultTransportManager中被调用)
                }
            }
        }

        return tcpSocketSender;
    }
    
    private void initialize(){
        System.out.println("=================================will start ChannelManager =======================================");
        //启动NIO通道，连接消息接收服务端
        m_manager = new ChannelManager(m_queue);
        //启动线程，对连接通道进行定期检测
        m_manager.start();
        System.out.println("================================ChannelManager started================================");
        
        //启动消息发送线程
        System.out.println("=================================will start TcpSocketSender Thread =======================================");
        RealMessageSender senderThread = new RealMessageSender();
        senderThread.start();
        System.out.println("=================================TcpSocketSender Thread Started=======================================");
    }

    private class RealMessageSender extends Thread {

        /**
         * 循环从消息队列中拿到一个个的消息，并通过NIO通道进行发送
         */
        @Override
        public void run() {
            while (true) {
                ChannelFuture channel = m_manager.channel();

                if (channel != null && checkWritable(channel)) {
                    try {
                        //从消息队列中获取一个消息
                        MessageTree tree = m_queue.poll();

                        if (tree != null) {
                            sendInternal(tree);
                            tree.setMessage(null);
                        }

                    } catch (Throwable t) {
                        LOG.error("TcpSocketSender.run:Error when sending message over TCP socket!,{}", t);
                    }
                } else {
                    try {
                        Thread.sleep(5);
                    } catch (Exception e) {
                        // ignore it
                        //m_active = false;
                    }
                }
            }
        }
    }

    /**
     * 发送消息（外部接口）
     *
     * @param tree
     * @return
     */
    public boolean send(MessageTree tree) {
        //如果可能的话,将anObject加到BlockingQueue里,即如果BlockingQueue可以容纳,则返回true,否则返回false. 
        //注意，这里并没有使用阻塞,主要是为了效率考虑，如果消息队列满了，则直接把消息抛弃
        boolean result = m_queue.offer(tree);

        if (!result) {
            logQueueFullInfo(tree);
        }
        return result;
    }

    /**
     * 记录由于消息队列满了，导致消息被抛弃的错误信息
     *
     * @param tree
     */
    private void logQueueFullInfo(MessageTree tree) {

        int count = m_errors.incrementAndGet();

        /**
         * 每1000条消息堆积则打出一个错误日志
         */
        if (count % 1000 == 0 || count == 1) {
            LOG.error("TcpSocketSender Message queue is full in tcp socket sender! Count: " + count);
        }

        tree = null;
    }

    private boolean checkWritable(ChannelFuture future) {
        boolean isWriteable = false;
        Channel channel = future.channel();

        if (future != null && channel.isOpen()) {
            if (channel.isActive() && channel.isWritable()) {
                isWriteable = true;
            } else {
                int count = m_attempts.incrementAndGet();

                if (count % 1000 == 0 || count == 1) {
                    LOG.info("TcpSocketSender.checkWritable:Netty write buffer is full! Attempts: {}" + count);
                }
            }
        }

        return isWriteable;
    }

    /**
     * 发送消息(内部使用)
     *
     * @param tree
     */
    private void sendInternal(MessageTree tree) {
        ChannelFuture future = m_manager.channel();
        //在业务线程中初始化内存池分配器，分配非堆内存
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer(10 * 1024); // 10K

        m_codec.encode(tree, buf);

        int size = buf.readableBytes();
        Channel channel = future.channel();
        //发送消息
        channel.writeAndFlush(buf);

        m_bytes += size;
        m_produced++;
    }

}
