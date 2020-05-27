/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core;

import io.netty.buffer.ByteBuf;

/**
 * 由Plexus容器自动启动,定义在了plexus的components.xml文件中了 IO缓冲读写器
 *
 * @author Administrator
 */
public class EscapingBufferWriter implements BufferWriter {

    public static final String ID = "escape";
    
    private volatile static BufferWriter bufferWriter;

    private EscapingBufferWriter() {

    }

    public static BufferWriter getBufferWriter() {
        if (bufferWriter == null) {
            synchronized (EscapingBufferWriter.class) {
                if (bufferWriter == null) {
                    bufferWriter = new EscapingBufferWriter();
                    //bufferWriter.initialize();   //进行初始化，运行进程
                }
            }
        }

        return bufferWriter;
    }

    @Override
    public int writeTo(ByteBuf buf, byte[] data) {
        int len = data.length;
        int count = len;
        int offset = 0;

        for (int i = 0; i < len; i++) {
            byte b = data[i];

            if (b == '\t' || b == '\r' || b == '\n' || b == '\\') {
                buf.writeBytes(data, offset, i - offset);
                buf.writeByte('\\');

                if (b == '\t') {
                    buf.writeByte('t');
                } else if (b == '\r') {
                    buf.writeByte('r');
                } else if (b == '\n') {
                    buf.writeByte('n');
                } else {
                    buf.writeByte(b);
                }

                count++;
                offset = i + 1;
            }
        }

        if (len > offset) {
            buf.writeBytes(data, offset, len - offset);
        }

        return count;
    }
}
