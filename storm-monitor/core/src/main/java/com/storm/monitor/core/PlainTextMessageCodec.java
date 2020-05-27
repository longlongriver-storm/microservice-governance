/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core;

import com.storm.monitor.core.common.MessageCodec;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.storm.monitor.core.entity.*;

/**
 * 缺省的消息编码器实现
 *
 * @author Administrator
 */
public class PlainTextMessageCodec implements MessageCodec {

    private static Logger m_logger = LoggerFactory.getLogger(PlainTextMessageCodec.class);

    public static final String ID = "plain-text";

    private static final String VERSION = "PT1"; // plain text version 1

    private static final byte TAB = '\t'; // tab character

    private static final byte LF = '\n'; // line feed character

    private BufferWriter m_writer = EscapingBufferWriter.getBufferWriter(); 

    private BufferHelper m_bufferHelper = new BufferHelper(m_writer);

    private DateHelper m_dateHelper = new DateHelper();

    private ThreadLocal<Context> m_ctx = new ThreadLocal<Context>() {
        @Override
        protected Context initialValue() {
            return new Context();
        }
    };

    /**
     * 对消息流进行解码
     *
     * @param buf
     * @return
     */
    @Override
    public MessageTree decode(ByteBuf buf) {
        MessageTree tree = new DefaultMessageTree();

        decode(buf, tree);
        return tree;
    }

    /**
     * 对单个消息树进行解码
     *
     * @param buf
     * @param tree
     */
    @Override
    public void decode(ByteBuf buf, MessageTree tree) {
        Context ctx = m_ctx.get().setBuffer(buf);

        decodeHeader(ctx, tree);
    }

    /**
     * 解码消息头
     *
     * @param ctx
     * @param tree
     */
    protected void decodeHeader(Context ctx, MessageTree tree) {
        BufferHelper helper = m_bufferHelper;
        String id = helper.read(ctx, TAB);
        String domain = helper.read(ctx, TAB);
        String hostName = helper.read(ctx, TAB);
        String ipAddress = helper.read(ctx, TAB);
        String threadGroupName = helper.read(ctx, TAB);
        String threadId = helper.read(ctx, TAB);
        String threadName = helper.read(ctx, TAB);
        long timestamp=Long.valueOf(helper.read(ctx, TAB));
        String message = helper.read(ctx, TAB);
        String messageId = helper.read(ctx, TAB);
        String messageType = helper.read(ctx, TAB);
        String parentMessageId = helper.read(ctx, TAB);
        String rootMessageId = helper.read(ctx, TAB);
        String sessionToken = helper.read(ctx, LF);

        if (VERSION.equals(id)) {
            tree.setDomain(domain);
            tree.setHostName(hostName);
            tree.setIpAddress(ipAddress);
            tree.setThreadGroupName(threadGroupName);
            tree.setThreadId(threadId);
            tree.setThreadName(threadName);
            tree.setTimestamp(timestamp);
            tree.setMessage(message);
            tree.setMessageId(messageId);
            tree.setMessageType(messageType);
            tree.setParentMessageId(parentMessageId);
            tree.setRootMessageId(rootMessageId);
            tree.setSessionToken(sessionToken);
        } else {
            throw new RuntimeException(String.format("Unrecognized id(%s) for plain text message codec!", id));
        }
    }

    /**
     * 对消息进行编码,写入缓存区中
     *
     * @param tree
     * @param buf
     */
    @Override
    public void encode(MessageTree tree, ByteBuf buf) {
        int count = 0;
        int index = buf.writerIndex();

        buf.writeInt(0); // place-holder占位符
        count += encodeHeader(tree, buf);

        buf.setInt(index, count);
    }

    /**
     * 对消息头进行编码
     *
     * @param tree
     * @param buf
     * @return
     */
    protected int encodeHeader(MessageTree tree, ByteBuf buf) {
        BufferHelper helper = m_bufferHelper;
        int count = 0;

        count += helper.write(buf, VERSION);
        count += helper.write(buf, TAB);
        count += helper.write(buf, tree.getDomain());
        count += helper.write(buf, TAB);
        count += helper.write(buf, tree.getHostName());
        count += helper.write(buf, TAB);
        count += helper.write(buf, tree.getIpAddress());
        count += helper.write(buf, TAB);
        count += helper.write(buf, tree.getThreadGroupName());
        count += helper.write(buf, TAB);
        count += helper.write(buf, tree.getThreadId());
        count += helper.write(buf, TAB);
        count += helper.write(buf, tree.getThreadName());
        count += helper.write(buf, TAB);
        count += helper.write(buf, Long.toString(tree.getTimestamp()));
        count += helper.write(buf, TAB);
        count += helper.writeRaw(buf, tree.getMessage());  //可以支持各种文本格式，消息主题内容必须用这个（通过UTF-8实现）
        count += helper.write(buf, TAB);
        count += helper.write(buf, tree.getMessageId());
        count += helper.write(buf, TAB);
        count += helper.write(buf, tree.getMessageType());
        count += helper.write(buf, TAB);
        count += helper.write(buf, tree.getParentMessageId());
        count += helper.write(buf, TAB);
        count += helper.write(buf, tree.getRootMessageId());
        count += helper.write(buf, TAB);
        count += helper.write(buf, tree.getSessionToken());
        count += helper.write(buf, LF);

        return count;
    }

    public void reset() {
        m_ctx.remove();
    }

    protected void setBufferWriter(BufferWriter writer) {
        m_writer = writer;
        m_bufferHelper = new BufferHelper(m_writer);
    }

    protected static class BufferHelper {

        private BufferWriter m_writer;

        public BufferHelper(BufferWriter writer) {
            m_writer = writer;
        }

        public String read(Context ctx, byte separator) {
            ByteBuf buf = ctx.getBuffer();
            char[] data = ctx.getData();
            int from = buf.readerIndex();
            int to = buf.writerIndex();
            int index = 0;
            boolean flag = false;

            for (int i = from; i < to; i++) {
                byte b = buf.readByte();

                if (b == separator) {
                    break;
                }

                if (index >= data.length) {
                    char[] data2 = new char[to - from];

                    System.arraycopy(data, 0, data2, 0, index);
                    data = data2;
                }

                char c = (char) (b & 0xFF);

                if (c > 127) {
                    flag = true;
                }

                if (c == '\\' && i + 1 < to) {
                    byte b2 = buf.readByte();

                    if (b2 == 't') {
                        c = '\t';
                        i++;
                    } else if (b2 == 'r') {
                        c = '\r';
                        i++;
                    } else if (b2 == 'n') {
                        c = '\n';
                        i++;
                    } else if (b2 == '\\') {
                        c = '\\';
                        i++;
                    } else {
                        // move back
                        buf.readerIndex(i + 1);
                    }
                }

                data[index] = c;
                index++;
            }

            if (!flag) {
                return new String(data, 0, index);
            } else {
                byte[] ba = new byte[index];

                for (int i = 0; i < index; i++) {
                    ba[i] = (byte) (data[i] & 0xFF);
                }

                try {
                    return new String(ba, 0, index, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    return new String(ba, 0, index);
                }
            }
        }

        public int write(ByteBuf buf, byte b) {
            buf.writeByte(b);
            return 1;
        }
        
        /**
         * 写入一个long类型的数据,long类型占8个字节（李鑫）
         * @param buf
         * @param l
         * @return 
         */
        public int write(ByteBuf buf,long l){
            buf.writeLong(l);
            return 8;
        }

        public int write(ByteBuf buf, String str) {
            if (str == null) {
                str = "null";
            }

            byte[] data = str.getBytes();

            buf.writeBytes(data);
            return data.length;
        }

        public int writeRaw(ByteBuf buf, String str) {
            if (str == null) {
                str = "null";
            }

            byte[] data;

            try {
                data = str.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                data = str.getBytes();
            }

            return m_writer.writeTo(buf, data);
        }
    }

    public static class Context {

        private ByteBuf m_buffer;

        private char[] m_data;

        public Context() {
            m_data = new char[4 * 1024 * 1024];
        }

        public ByteBuf getBuffer() {
            return m_buffer;
        }

        public char[] getData() {
            return m_data;
        }

        public Context setBuffer(ByteBuf buffer) {
            m_buffer = buffer;
            return this;
        }
    }

    /**
     * Thread safe date helper class. DateFormat is NOT thread safe.
     */
    protected static class DateHelper {

        private BlockingQueue<SimpleDateFormat> m_formats = new ArrayBlockingQueue<SimpleDateFormat>(20);

        private Map<String, Long> m_map = new ConcurrentHashMap<String, Long>();

        public String format(long timestamp) {
            //获取并移除队列的头元素，如果队列为空，则返回一个null元素(不阻塞)
            SimpleDateFormat format = m_formats.poll();

            if (format == null) {
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            }

            try {
                return format.format(new Date(timestamp));
            } finally {
                if (m_formats.remainingCapacity() > 0) {   //如果容量够，则存入
                    m_formats.offer(format);
                }
            }
        }

        public long parse(String str) {
            int len = str.length();
            String date = str.substring(0, 10);
            Long baseline = m_map.get(date);

            if (baseline == null) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                    baseline = format.parse(date).getTime();
                    m_map.put(date, baseline);
                } catch (ParseException e) {
                    return -1;
                }
            }

            long time = baseline.longValue();
            long metric = 1;
            boolean millisecond = true;

            for (int i = len - 1; i > 10; i--) {
                char ch = str.charAt(i);

                if (ch >= '0' && ch <= '9') {
                    time += (ch - '0') * metric;
                    metric *= 10;
                } else if (millisecond) {
                    millisecond = false;
                } else {
                    metric = metric / 100 * 60;
                }
            }
            return time;
        }
    }

    protected static enum Policy {
        DEFAULT,
        WITHOUT_STATUS,
        WITH_DURATION;

        public static Policy getByMessageIdentifier(byte identifier) {
            switch (identifier) {
                case 't':
                    return WITHOUT_STATUS;
                case 'T':
                case 'A':
                    return WITH_DURATION;
                case 'E':
                case 'H':
                    return DEFAULT;
                default:
                    return DEFAULT;
            }
        }
    }

   
}
