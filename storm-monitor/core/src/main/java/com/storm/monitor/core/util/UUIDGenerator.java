/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.util;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UUIDGenerator {

    /**
     * 产生一个32位的UUID
     *
     * @return
     */
    public static String generate() {
        return new StringBuilder(32).append(format(getIP()))
                .append(format(getJVM())).append(format(getHiTime()))
                .append(format(getLoTime())).append(format(getCount()))
                .toString();
    }

    private static final int IP;

    static {
        int ipadd;
        try {
            ipadd = toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }

    private static short counter = (short) 0;

    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

    private final static String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuilder buf = new StringBuilder("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    private final static String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuilder buf = new StringBuilder("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    private final static int getJVM() {
        return JVM;
    }

    private final static short getCount() {
        synchronized (UUIDGenerator.class) {
            if (counter < 0) {
                counter = 0;
            }
            return counter++;
        }
    }

    /**
     * Unique in a local network
     */
    private final static int getIP() {
        return IP;
    }

    /**
     * Unique down to millisecond
     */
    private final static short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    private final static int getLoTime() {
        return (int) System.currentTimeMillis();
    }

    private final static int toInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    /**
     * 生成一个40位的UUID,前面8位是日期
     *
     * @return
     */
    public static String generateUUID40() {
        SimpleDateFormat formatUUDIDate = new SimpleDateFormat("yyyyMMdd");
        StringBuilder sb = new StringBuilder();
        sb.append(formatUUDIDate.format(new Date()));
        sb.append(generate());
        return sb.toString();
    }

    /**
     * 生成一个32位的UUID
     *
     * @return
     */
    public static String generateUUID32() {
        return generate();
    }

//    public static void main(String[] args) {
//        for (int i = 0; i < 1000; i++) {
//            System.out.println(UUIDGenerator.generate());
//        }
//    }
}
