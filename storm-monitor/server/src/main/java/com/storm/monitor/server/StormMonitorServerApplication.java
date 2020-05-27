/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath*:META-INF/springxml/service-config.xml"})
@SpringBootApplication
public class StormMonitorServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StormMonitorServerApplication.class, args);
	}
}
