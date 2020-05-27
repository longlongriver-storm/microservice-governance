/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.server.receiver;

import com.storm.monitor.core.PlainTextMessageCodec;
import com.storm.monitor.core.common.MessageCodec;
import com.storm.monitor.core.common.Common;
import com.storm.monitor.core.entity.DefaultMessageTree;
import com.storm.monitor.core.server.MessageHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import io.netty.handler.codec.ByteToMessageDecoder;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author lixin
 */
public class TcpSocketReceiver {
    private static final Logger m_logger = LoggerFactory.getLogger(TcpSocketReceiver.class);
    
    /**
     * 成功处理的消息总量
     */
    private volatile long m_processCount;
    /**
     * 异常处理的消息总量
     */
    private volatile long m_messageTotalLoss;
    
    /**
     * 接收服务器第一次启动时间
     */
    private long firstStartServerTime=0;

    private EventLoopGroup m_bossGroup;

    private EventLoopGroup m_workerGroup;

    private ChannelFuture m_future;

    /**
     * NIO消息处理Handler，调用消息消费者对消息进行消费,采用DefaultMessageHandler，具体的消息分期、分析都是在其中进行的
     */
    private MessageHandler m_handler;

    /**
     * 消息编码解码器，默认采用PlainTextMessageCodec子类型
     */
    private MessageCodec m_codec;
    /**
     * 和日志收集服务端的通讯端口
     */
    private int m_port;


    public TcpSocketReceiver() {
        m_handler = new DefaultMessageHandler();
        m_codec = new PlainTextMessageCodec();
    }

    /**
     * 进行初始化,启动接收服务
     */
    public void init(int port) {
        try {
            m_port=port;
            m_logger.info("TcpSocketReciever listen on port:{}",m_port);
            startServer(m_port);
        } catch (Exception e) {
            m_logger.error("TcpSocketReceiver.init errorMessage={},error={}",e.getMessage(), e);
        }
    }

    /**
     * 被注册成系统钩子，在JVM关闭的时候会被调用 主要关闭NIO服务
     */
    public synchronized void destory() {
        try {
            m_logger.info("start shutdown socket, port " + m_port);
            m_future.channel().closeFuture();
            m_bossGroup.shutdownGracefully();
            m_workerGroup.shutdownGracefully();
            m_logger.info("shutdown socket success");
        } catch (Exception e) {
            m_logger.error("TcpSocketReciever.destory error,errorMessage={},error={}",e.getMessage(), e);
        }
    }

    /**
     * 启动接收服务（NIO）
     *
     * @param port 服务监听端口
     * @throws InterruptedException
     */
    public synchronized void startServer(int port) throws InterruptedException {
        boolean linux = getOSMatches("Linux") || getOSMatches("LINUX");
        int threads = 24;
        ServerBootstrap bootstrap = new ServerBootstrap();

        m_bossGroup = linux ? new EpollEventLoopGroup(threads) : new NioEventLoopGroup(threads);
        m_workerGroup = linux ? new EpollEventLoopGroup(threads) : new NioEventLoopGroup(threads);
        bootstrap.group(m_bossGroup, m_workerGroup);
        bootstrap.channel(linux ? EpollServerSocketChannel.class : NioServerSocketChannel.class);

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();

                pipeline.addLast("decode", new MessageDecoder());   //自定义的消息解码器，解码后，调用消息消费者对消息进行消费
            }
        });

        m_logger.info(".............................................");

        bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

        try {
            m_future = bootstrap.bind(port).sync();
            m_logger.info("TcpSocketReceiver.startServer:start netty server success!");
        } catch (Exception e) {
            m_logger.error("TcpSocketReceiver.startServer:Started Netty Server Failed,port={},error={}",port, e);
        }
        if(firstStartServerTime==0){
            firstStartServerTime=System.currentTimeMillis();
        }
    }

    protected boolean getOSMatches(String osNamePrefix) {
        String os = System.getProperty("os.name");

        if (os == null) {
            return false;
        }
        return os.startsWith(osNamePrefix);
    }

/**
 * 消息解码器
 */
public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        if (buffer.readableBytes() < 4) {
            return;
        }
        buffer.markReaderIndex();
        int length = buffer.readInt();
        buffer.resetReaderIndex();
        if (buffer.readableBytes() < length + 4) {
            return;
        }
        try {
            if (length > 0) {
                ByteBuf readBytes = buffer.readBytes(length + 4);
                readBytes.markReaderIndex();
                readBytes.readInt();

                DefaultMessageTree tree = (DefaultMessageTree) m_codec.decode(readBytes);

                readBytes.resetReaderIndex();
                tree.setBuffer(readBytes);
                //调用消息消费者对消息进行消费
                m_handler.handle(tree);
                //成功处理的消息总量
                m_processCount++;
                long flag = m_processCount % Common.SUCCESS_COUNT;

                if (flag == 0) {
                    m_logger.info("TcpSocketReceiver.MessageDecoder.decode current static,"
                        + "all_message_process_count={},message_total_loss={}",m_processCount,m_messageTotalLoss);
                }
            } else {
                // client message is error
                buffer.readBytes(length);
            }
        } catch (Exception e) {
            m_messageTotalLoss++;
            m_logger.error("MessageDecoder.decode errorMessage={},error={}",e.getMessage(), e);
        }
    }
}
    
    /**
     * 获取消息处理服务器的情况
     * @return 
     */
    public String getInfo(){
        SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String info="<h1>Receive Server Info:</h1>";
        info +="<ul>";
        info +="<li>Server First Start Time:"+format_date.format(firstStartServerTime)+"</li>";
        info +="<li>ChannelFuture.isCancellable:"+m_future.isCancellable()+"</li>";
        info +="<li>ChannelFuture.isCancellable:"+m_future.isCancelled()+"</li>";
        info +="<li>ChannelFuture.isDone:"+m_future.isDone()+"</li>";
        info +="<li>ChannelFuture.isSuccess:"+m_future.isSuccess()+"</li>";
        info +="<li>ChannelFuture.Channel:"+(m_future.channel()==null?"no channel":"have channel")+"</li>";
        info +="<li>ChannelFuture.Channel.isActive:"+(m_future.channel()==null?"no":m_future.channel().isActive())+"</li>";
        info +="<li>ChannelFuture.Channel.isOpen:"+(m_future.channel()==null?"no":m_future.channel().isOpen())+"</li>";
        info +="<li>ChannelFuture.Channel.isRegistered:"+(m_future.channel()==null?"no":m_future.channel().isRegistered())+"</li>";
        info +="<li>ChannelFuture.Channel.isWritable:"+(m_future.channel()==null?"no":m_future.channel().isWritable())+"</li>";
        info +="</ul>";
        info +="<h1>Message Handler Info:</h1>";
        info +="<ul>";
        info +="<li>All Process Message Count(Success):"+m_processCount+"</li>";
        info +="<li>All Loss Message Count(Error):"+m_messageTotalLoss+"</li>";
        info +="</ul>";
        info +=m_handler.getInfo();
        format_date=null;
        return info;
    }
}
