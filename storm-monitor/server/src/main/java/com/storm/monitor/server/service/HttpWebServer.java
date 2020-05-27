/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.server.service;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * 轻量HTTP监控接口
 * @author lixin
 */
public class HttpWebServer {

    /**
     * 监听端口
     */
    HttpServer httpServer;
    ApmReportBuilder apmReportBuilder;

    public HttpWebServer() {
    }

    public void startHttpServer(int port, ApmReportBuilder _apmReportBuilder) throws Exception {
        // 创建 http 服务器, 绑定端口
        this.httpServer = HttpServer.create(new InetSocketAddress(port), 1);
        this.apmReportBuilder = _apmReportBuilder;

        // 创建上下文监听, "/apmtest/getinfo" 表示匹配所有 URI 请求
        httpServer.createContext("/apmtest/getinfo", new HttpHandler() {
            @Override
            public void handle(HttpExchange httpExchange) throws IOException {
                // 响应内容
                byte[] respContents = apmReportBuilder.getApmServerInfo().getBytes("UTF-8");
                // 设置响应头
                httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                // 设置响应code和内容长度
                httpExchange.sendResponseHeaders(200, respContents.length);
                // 设置响应内容
                httpExchange.getResponseBody().write(respContents);
                // 关闭处理器, 同时将关闭请求和响应的输入输出流（如果还没关闭）
                httpExchange.close();
            }
        });
        //设置服务器的线程池对象
        httpServer.setExecutor(Executors.newFixedThreadPool(1));
        // 启动服务
        httpServer.start();
    }

    public void stopHttpServer() {
        try {
            if (httpServer != null) {
                httpServer.stop(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
