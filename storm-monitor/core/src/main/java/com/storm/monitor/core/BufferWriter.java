package com.storm.monitor.core;

import io.netty.buffer.ByteBuf;

/**
 * IO读写器接口
 * @author lixin
 */
public interface BufferWriter {
	public int writeTo(ByteBuf buf, byte[] data);
}
