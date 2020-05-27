/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.client;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 获取系统有效IP地址,如果没有指定IP的话，就必须进行很复杂的计算才能获取
 * 多网卡配置的时候,准确获取地址
 * 通过：枚举多网卡判断，然后结合IP4的地址段做区分
 * 规则：
 * <ul>
 *      <li>1:本地地址比其他地址有更高的优先级</li>
 *      <li>2:有域名的本地地址比其它本地地址具有更高的优先级</li>
 * </ul>
 * @author Administrator
 */
public enum NetworkInterfaceManager {
	INSTANCE;
        /**系统有效IP地址,尤其是在多网卡配置的时候,必须通过计算，才能准确获取地址*/
	private InetAddress m_local;

	private NetworkInterfaceManager() {
		load();
	}

	public InetAddress findValidateIp(List<InetAddress> addresses) {
		InetAddress local = null;
		for (InetAddress address : addresses) {
			if (address instanceof Inet4Address) {
				if (address.isLoopbackAddress() || address.isSiteLocalAddress()) {
					if (local == null) {
						local = address;
					} else if (address.isSiteLocalAddress() && !address.isLoopbackAddress()) {
                                                //本地地址比其他地址有更高的优先级
						// site local address has higher priority than other address
						local = address;
					} else if (local.isSiteLocalAddress() && address.isSiteLocalAddress()) {
                                                //有域名的本地地址比其它本地地址具有更高的优先级
						// site local address with a host name has higher
						// priority than one without host name
						if (local.getHostName().equals(local.getHostAddress())
						      && !address.getHostName().equals(address.getHostAddress())) {
							local = address;
						}
					}
				} else {
					if (local == null) {
						local = address;
					}
				}
			}
		}
		return local;
	}

	public String getLocalHostAddress() {
		return m_local.getHostAddress();
	}

	public String getLocalHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return m_local.getHostName();
		}
	}

	private String getProperty(String name) {
		String value = null;

		value = System.getProperty(name);
		
		if (value == null) {
                        //我们可能在bat文件或者CMD中设置一些临时系统变量，譬如set XXX = XXX
                        //此时可以用System.getenv(XXX)可以获取set XXX的值
			value = System.getenv(name);
		}

		return value;
	}

        /**
         * 获取系统有效IP地址,如果没有指定IP的话，就必须进行很复杂的计算才能获取
         */
	private void load() {
                //在系统环境上下文中是否设置了变量“host.ip”，用来指定当前主机IP
		String ip = getProperty("host.ip");

		if (ip != null) {   //指定当前主机IP
			try {
				m_local = InetAddress.getByName(ip);
				return;
			} catch (Exception e) {
				System.err.println(e);
				// ignore
			}
		}

                /***********************************************
                 *  没有指定IP的话，就必须进行很复杂的计算才能获取
                 ***********************************************/
		try {
			List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
			List<InetAddress> addresses = new ArrayList<InetAddress>();
			InetAddress local = null;

			try {
				for (NetworkInterface ni : nis) {
					if (ni.isUp()) {
						addresses.addAll(Collections.list(ni.getInetAddresses()));
					}
				}
				local = findValidateIp(addresses);
			} catch (Exception e) {
				// ignore
			}
			m_local = local;
		} catch (SocketException e) {
			// ignore it
		}
	}
}
