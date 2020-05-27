/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.client;

import com.storm.monitor.core.common.Common;
import com.storm.monitor.core.entity.MessageTree;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NIO通道管理器
 *
 * @author lixin
 */
public class ChannelManager extends Thread {

    /**
     * 普通日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(ChannelManager.class);

    /**
     * 客户端配置管理
     */
    private Bootstrap m_bootstrap;

    private boolean m_active = true;

    private int m_retriedTimes = 0;

    /**
     * 当前系统内存中的消息队列，是个有界队列
     */
    private BlockingQueue<MessageTree> m_queue;

    /**
     * 对远程服务器可用性进行检测的次数
     */
    private int m_count = -10;

    private ChannelHolder m_activeChannelHolder;

    public ChannelManager(BlockingQueue<MessageTree> queue) {
        m_queue = queue;

        long start_t = System.currentTimeMillis();
        //NioEventLoopGroup可以理解为一个线程池，内部维护了一组线程，每个线程负责处理多个Channel上的事件，而一个Channel只对应于一个线程，这样可以回避多线程下的数据同步问题。
        EventLoopGroup group = new NioEventLoopGroup(1);
        //启动类，
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
            }
        });
        m_bootstrap = bootstrap;

        //获取服务地址配置
        String serverConfig = loadServerConfig();
        //解析服务地址
        List<InetSocketAddress> configedAddresses = parseSocketAddress(serverConfig);
        //初始化连接
        ChannelHolder holder = initChannel(configedAddresses, serverConfig);

        if (holder != null) {
            m_activeChannelHolder = holder;
        } else {
            m_activeChannelHolder = new ChannelHolder();
            m_activeChannelHolder.setServerAddresses(configedAddresses);
        }

        LOG.info("ChannelManager init success,using time:{}ms", System.currentTimeMillis() - start_t);
    }

    /**
     * 初始化连接通道
     *
     * @param addresses
     * @param serverConfig
     * @return
     */
    private ChannelHolder initChannel(List<InetSocketAddress> addresses, String serverConfig) {
        try {
            int len = addresses.size();

            //如果有多个可用的地址，则那个地址先创建成功连接了，则就用那个地址的连接,并直接返回，不再试其它地址
            for (int i = 0; i < len; i++) {
                InetSocketAddress address = addresses.get(i);
                String hostAddress = address.getAddress().getHostAddress();
                ChannelHolder holder = null;

                if (m_activeChannelHolder != null && hostAddress.equals(m_activeChannelHolder.getIp())) {
                    holder = new ChannelHolder();
                    holder.setActiveFuture(m_activeChannelHolder.getActiveFuture()).setConnectChanged(false);
                } else {
                    //基于netty创建和远程服务器的连接
                    ChannelFuture future = createChannel(address);

                    if (future != null) {
                        holder = new ChannelHolder();
                        holder.setActiveFuture(future).setConnectChanged(true);
                    }
                }

                if (holder != null) {
                    //如果成功,必须执行到这里
                    holder.setActiveIndex(i).setIp(hostAddress);
                    holder.setActiveServerConfig(serverConfig).setServerAddresses(addresses);

                    LOG.info("success when init Monitor Client, new active holder" + holder.toString());
                    return holder;
                }
            }
        } catch (Exception e) {
            LOG.error("ChannelManager initChannel:{}", e);
        }

        //*******************************************************************
        //******   如果执行到这里了，说明没有可用的地址，则报错退出   *******
        //*******************************************************************
        try {
            StringBuilder sb = new StringBuilder();

            for (InetSocketAddress address : addresses) {
                sb.append(address.toString()).append(";");
            }
            LOG.info("Error when init APM server Connection" + sb.toString());
        } catch (Exception e) {
            // ignore
        }
        return null;
    }

    /**
     * 创建和新的服务端的NIO连接通道（基于netty）
     *
     * @param address
     * @return
     */
    private ChannelFuture createChannel(InetSocketAddress address) {
        ChannelFuture future = null;

        try {
            future = m_bootstrap.connect(address);
            future.awaitUninterruptibly(100, TimeUnit.MILLISECONDS); // 100 ms

            if (!future.isSuccess()) {
                LOG.info("ChannelManager createChannel:Error when try connecting to " + address);
                closeChannel(future);
            } else {
                LOG.info("ChannelManager createChannel:Connected to APM server at " + address);
                return future;
            }
        } catch (Throwable e) {
            LOG.error("ChannelManager createChannel:Error when connect server " + address.getAddress() + ",{}", e);

            if (future != null) {
                closeChannel(future);
            }
        }
        return null;
    }

    /**
     * 关闭NIO通道
     *
     * @param channel
     */
    private void closeChannel(ChannelFuture channel) {
        try {
            if (channel != null) {
                LOG.info("close channel " + channel.channel().remoteAddress());
                channel.channel().close();
            }
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * 关闭NIO通道持有代理对象
     *
     * @param channelHolder
     */
    private void closeChannelHolder(ChannelHolder channelHolder) {
        try {
            ChannelFuture channel = channelHolder.getActiveFuture();

            closeChannel(channel);
            channelHolder.setActiveIndex(-1);
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * TODO，特例，最终要被改成配置的,通用格式类似：127.0.0.1:20280;10.27.1.133:20280
     *
     * @return
     */
    private String loadServerConfig() {
        //HashMap<String, String> apmClientCfg = ApplicationContextUtil.getBean(Common.SPRING_BEAN_KEY);
        //return apmClientCfg == null ? null : apmClientCfg.get(Common.SPRING_KEY_CLIENT_CONFIG_SERVER_ADDRESS);
        return Common.ServerAddress;
    }

    /**
     * 解析服务端地址
     *
     * @param content
     * @return
     */
    private List<InetSocketAddress> parseSocketAddress(String content) {
        try {
            String[] strs = content.split(";");
            List<InetSocketAddress> address = new ArrayList<InetSocketAddress>();

            for (String str : strs) {
                String[] items = str.split(":");

                address.add(new InetSocketAddress(items[0], Integer.parseInt(items[1])));
            }
            return address;
        } catch (Exception e) {
            LOG.error("ChannelManager parseSocketAddress is error,{}", e);
        }
        return new ArrayList<InetSocketAddress>();
    }

    /**
     * 获得一个NIO可用的渠道
     *
     * @return
     */
    public ChannelFuture channel() {
        if (m_activeChannelHolder != null) {
            return m_activeChannelHolder.getActiveFuture();
        } else {
            return null;
        }
    }

    /**
     * 以下2种情况下需要对远程服务器的配置进行重新检测:
     * <ol>
     * <li>10*30=300秒间隔（5分钟）</li>
     * <li>连接对象发生变化了</li>
     * </ol>
     *
     * @param count
     * @return
     */
    private boolean shouldCheckServerConfig(int count) {
        int duration = 30;

        if (count % duration == 0 || m_activeChannelHolder.getActiveIndex() == -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检测当前的连接是否可用，如果连续5次检测到消息队列堆积，说明是真的堆积了。
     *
     * @param activeFuture
     * @return
     */
    private boolean isChannelDisabled(ChannelFuture activeFuture) {
        return activeFuture != null && !activeFuture.channel().isOpen();
    }

    /**
     * 检测是否有消息堆积，默认不堆积
     *
     * @param activeFuture
     * @return
     */
    private boolean isChannelStalled(ChannelFuture activeFuture) {
        //return false;

        m_retriedTimes++;

        int size = m_queue.size();
        boolean stalled = activeFuture != null && size >= TcpSocketSender.SIZE - 10;

        if (stalled) {
            if (m_retriedTimes >= 5) {
                m_retriedTimes = 0;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 检测服务端的地址是否发生变化了
     */
    private void checkServerChanged() {
        //以下2种情况下需要对远程服务器的配置进行重新检测:1、5分钟检测一次；2、连接对象发生变化了
        if (shouldCheckServerConfig(++m_count)) {
            String servers = loadServerConfig();

            if (servers != null && m_activeChannelHolder.getActiveServerConfig().equals(servers) == false) {

                List<InetSocketAddress> serverAddresses = parseSocketAddress(servers);
                ChannelHolder newHolder = initChannel(serverAddresses, servers);

                if (newHolder != null) {
                    if (newHolder.isConnectChanged()) {
                        ChannelHolder last = m_activeChannelHolder;

                        m_activeChannelHolder = newHolder;
                        closeChannelHolder(last);
                        LOG.info("switch active channel to " + m_activeChannelHolder);
                    } else {
                        m_activeChannelHolder = newHolder;
                    }
                }
            }
        }
    }

    /**
     * 对现有的远程服务器的连接进行双重检测（不可用 或者 僵死）
     *
     * @param activeFuture
     */
    private void doubleCheckActiveServer(ChannelFuture activeFuture) {
        try {
            if (isChannelStalled(activeFuture) || isChannelDisabled(activeFuture)) {
                closeChannelHolder(m_activeChannelHolder);
            }
        } catch (Throwable e) {
            LOG.error("ChannelManager.doubleCheckActiveServer error,{},{}", e.getMessage(), e);
        }
    }

    private void reconnectDefaultServer(ChannelFuture activeFuture, List<InetSocketAddress> serverAddresses) {
        try {
            int reconnectServers = m_activeChannelHolder.getActiveIndex();

            if (reconnectServers == -1) {
                reconnectServers = serverAddresses.size();
            }
            for (int i = 0; i < reconnectServers; i++) {
                ChannelFuture future = createChannel(serverAddresses.get(i));

                if (future != null) {
                    ChannelFuture lastFuture = activeFuture;

                    m_activeChannelHolder.setActiveFuture(future);
                    m_activeChannelHolder.setActiveIndex(i);
                    closeChannel(lastFuture);
                    break;
                }
            }
        } catch (Throwable e) {
            LOG.error("ChannelManager.reconnectDefaultServer error,{},{}", e.getMessage(), e);
        }
    }

    /**
     * 对NIO的联通通道要定期检测
     * <ol>
     * <li>•检查Server列表是否变更</li>
     * 每间隔10s，检查当前channelFuture是否活跃，活跃，则300s检查一次，不活跃，则执行检查。
     * 检查的逻辑是：比较本地server列表跟远程服务提供的列表是否相等，不相等则根据远程服务提供的server
     * 列表顺序的重新建立第一个能用的ChannelFuture
     *
     * <li>•查看当前客户端是否有积压，或者ChannelFuture是否被关闭</li>
     * 如果有积压，或者关闭掉了，则关闭当前连接，将activeIndex=-1，表示当前连接不可用。
     *
     * <li>•重连默认Server</li>
     * 从0到activeIndex中找一个能连接的server，中心建立一个连接。如果activeIndex为-1，则从整个的server列表中
     * 顺序的找一个可用的连接建立连接。
     * </ol>
     */
    @Override
    public void run() {
        while (m_active) {
            // make save message id index asyc
            //m_idfactory.saveMark();
            //目前这个配置不会有变化,预留做动态地址加载的时候会用上
            checkServerChanged();

            ChannelFuture activeFuture = m_activeChannelHolder.getActiveFuture();
            List<InetSocketAddress> serverAddresses = m_activeChannelHolder.getServerAddresses();

            doubleCheckActiveServer(activeFuture);
            reconnectDefaultServer(activeFuture, serverAddresses);

            try {
                Thread.sleep(10 * 1000L); // check every 10 seconds
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    public static class ChannelHolder {

        private ChannelFuture m_activeFuture;

        private int m_activeIndex = -1;

        private String m_activeServerConfig;

        private List<InetSocketAddress> m_serverAddresses;

        private String m_ip;
        /**
         * NIO连接是否发生变化了
         */
        private boolean m_connectChanged;

        public ChannelFuture getActiveFuture() {
            return m_activeFuture;
        }

        public int getActiveIndex() {
            return m_activeIndex;
        }

        public String getActiveServerConfig() {
            return m_activeServerConfig;
        }

        public String getIp() {
            return m_ip;
        }

        public List<InetSocketAddress> getServerAddresses() {
            return m_serverAddresses;
        }

        public boolean isConnectChanged() {
            return m_connectChanged;
        }

        public ChannelHolder setActiveFuture(ChannelFuture activeFuture) {
            m_activeFuture = activeFuture;
            return this;
        }

        public ChannelHolder setActiveIndex(int activeIndex) {
            m_activeIndex = activeIndex;
            return this;
        }

        public ChannelHolder setActiveServerConfig(String activeServerConfig) {
            m_activeServerConfig = activeServerConfig;
            return this;
        }

        public ChannelHolder setConnectChanged(boolean connectChanged) {
            m_connectChanged = connectChanged;
            return this;
        }

        public ChannelHolder setIp(String ip) {
            m_ip = ip;
            return this;
        }

        public ChannelHolder setServerAddresses(List<InetSocketAddress> serverAddresses) {
            m_serverAddresses = serverAddresses;
            return this;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append("active future :").append(m_activeFuture.channel().remoteAddress());
            sb.append(" index:").append(m_activeIndex);
            sb.append(" ip:").append(m_ip);
            sb.append(" server config:").append(m_activeServerConfig);
            return sb.toString();
        }
    }

}
