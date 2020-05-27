/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.demo;

import org.springframework.context.annotation.ImportResource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@ImportResource({"classpath*:META-INF/springxml/service-config.xml"})
@SpringBootApplication
public class StormMonitorClientDemoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StormMonitorClientDemoApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(StormMonitorClientDemoApplication.class, args);
    }
}
