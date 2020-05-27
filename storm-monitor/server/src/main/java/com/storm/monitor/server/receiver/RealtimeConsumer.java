/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.server.receiver;

import com.storm.monitor.core.entity.MessageTree;
import com.storm.monitor.core.server.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author lixin
 */
public class RealtimeConsumer implements MessageConsumer {

    private static Logger LOG = LoggerFactory.getLogger(RealtimeConsumer.class);

    public static final long MINUTE = 60 * 1000L;

    public static final long HOUR = 60 * MINUTE;

    /**
     * 消息队列大小
     */
    public static final int SIZE = 3000;

    private long msgCount = 0;
    private long nullMsgCount = 0;
    private long errorDistributeCount = 0;
    private long startTime = 0;

    /**
     * 异常事件队列（系统异常，业务异常）
     */
    private BlockingQueue<MessageTree> errorEventQueue;
    /**
     * 服务访问分钟统计数据
     */
    private BlockingQueue<MessageTree> serviceMinuteStatQueue;
    /**
     * dao访问分钟统计数据
     */
    private BlockingQueue<MessageTree> daoMinuteStatQueue;
    /**
     * 系统监控事件队列
     */
    private BlockingQueue<MessageTree> systemMonitorQueue;
    /**
     * 自定义采集数据队列
     */
    private BlockingQueue<MessageTree> customPickDataQueue;

    ConsumerTaskForServiceMessage c1;
    ConsumerTaskForDaoMessage c2;
    ConsumerTaskForErrorMessage c3;
    ConsumerTaskForSystemMessage c4;
    ConsumerTaskForDataPickMessage c5;

    public RealtimeConsumer() {
        init();
    }

    /**
     * 消息队列及处理线程初始化
     */
    private void init() {
        errorEventQueue = new LinkedBlockingQueue<MessageTree>(SIZE);
        serviceMinuteStatQueue = new LinkedBlockingQueue<MessageTree>(SIZE * 5);
        daoMinuteStatQueue = new LinkedBlockingQueue<MessageTree>(SIZE * 5);
        systemMonitorQueue = new LinkedBlockingQueue<MessageTree>(SIZE);
        customPickDataQueue = new LinkedBlockingQueue<MessageTree>(SIZE * 5);

        c1 = new ConsumerTaskForServiceMessage(serviceMinuteStatQueue);
        c1.setName("apm-consumer-task-service");
        c1.start();

        c2 = new ConsumerTaskForDaoMessage(daoMinuteStatQueue);
        c2.setName("apm-consumer-task-dao");
        c2.start();

        c3 = new ConsumerTaskForErrorMessage(errorEventQueue);
        c3.setName("apm-consumer-task-error");
        c3.start();

        c4 = new ConsumerTaskForSystemMessage(systemMonitorQueue);
        c4.setName("apm-consumer-task-system");
        c4.start();

        c5 = new ConsumerTaskForDataPickMessage(customPickDataQueue);
        c5.setName("apm-consumer-task-customdatapick");
        c5.start();
    }

    /**
     * 处理一个消息
     *
     * @param tree
     */
    @Override
    public void consume(MessageTree tree) {
        msgCount++;
        if (tree != null) {
            if (distributeMessage(tree) == false) {
                errorDistributeCount++;
            }
        } else {
            nullMsgCount++;
        }
        //处理10000条消息则写一条处理日志
        if (msgCount % 10000L == 0L) {
            LOG.info("RealtimeConsumer.consume,total message count is {},null message count is {},distribute error message count is {} ", msgCount, nullMsgCount, errorDistributeCount);
        }
    }

    /**
     * 将消息分发到不同的消息队列中
     *
     * @param tree
     * @return
     */
    private boolean distributeMessage(MessageTree tree) {
        boolean result = false;
        if (MessageTree.MESSAGE_TYPE_CUSTOM_PICK.equals(tree.getMessageType())) {
            result = customPickDataQueue.offer(tree);
        } else if (MessageTree.MESSAGE_TYPE_DAO.equals(tree.getMessageType())) {
            result = daoMinuteStatQueue.offer(tree);
        } else if (MessageTree.MESSAGE_TYPE_ERROR.equals(tree.getMessageType())) {
            result = errorEventQueue.offer(tree);
        } else if (MessageTree.MESSAGE_TYPE_SERVICE.equals(tree.getMessageType())) {
            result = serviceMinuteStatQueue.offer(tree);
        } else if (MessageTree.MESSAGE_TYPE_SYSTEM.equals(tree.getMessageType())) {
            result = systemMonitorQueue.offer(tree);
        }
        return result;
    }

    /**
     * 被加入系统关闭的钩子中，在系统关闭的时候会被调用
     */
    @Override
    public void doCheckpoint() {

    }

    /**
     * 获取消息消费情况
     *
     * @return
     */
    @Override
    public String getInfo() {
        String info = "<table border=1 width='90%'><tr><th>Queue Name</th><th>Queue Size</th><th>Total Handle</th><th>Success Handle</th><th>Error Handle</th></tr>";
        
        info += "<tr><td>serviceMinuteStatQueue</td><td>" + serviceMinuteStatQueue.size() + "</td><td>"+(c1.getAllSaveCount()+c1.getErrorSaveCount())+"</td><td>"+c1.getAllSaveCount()+"</td><td>"+c1.getErrorSaveCount()+"</td></tr>";
        info += "<tr><td>daoMinuteStatQueue</td><td>" + daoMinuteStatQueue.size() + "</td><td>"+(c2.getAllSaveCount()+c2.getErrorSaveCount())+"</td><td>"+c2.getAllSaveCount()+"</td><td>"+c2.getErrorSaveCount()+"</td></tr>";
        info += "<tr><td>errorEventQueue</td><td>" + errorEventQueue.size() + "</td><td>"+(c3.getAllSaveCount()+c3.getErrorSaveCount())+"</td><td>"+c3.getAllSaveCount()+"</td><td>"+c3.getErrorSaveCount()+"</td></tr>";
        info += "<tr><td>systemMonitorQueue</td><td>" + systemMonitorQueue.size() + "</td><td>"+(c4.getAllSaveCount()+c4.getErrorSaveCount())+"</td><td>"+c4.getAllSaveCount()+"</td><td>"+c4.getErrorSaveCount()+"</td></tr>";
        info += "<tr><td>customPickDataQueue</td><td>" + customPickDataQueue.size() + "</td><td>"+(c5.getAllSaveCount()+c5.getErrorSaveCount())+"</td><td>"+c5.getAllSaveCount()+"</td><td>"+c5.getErrorSaveCount()+"</td></tr>";

        info += "</table>";
        return info;
    }
}
