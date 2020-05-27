/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.client;

import com.storm.monitor.core.entity.EventMessage;

/**
 * 事件、消息、日志采集器(小蜜蜂)
 *
 * @author lixin
 */
public class Bee {

	/**
	 * 事件
	 *
	 * @param em 事件
	 */
	public static void pickEventMessage(EventMessage em) {
		Honeycomb.getIstance().send(em);
	}

}
