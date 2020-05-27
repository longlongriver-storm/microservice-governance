package com.storm.monitor.core.entity;

public interface MessageTree extends Cloneable {
    /**
     * 服务访问的分钟统计
     */
    public static final String MESSAGE_TYPE_SERVICE="service";
    /**
     * dao访问的分钟统计
     */
    public static final String MESSAGE_TYPE_DAO="dao";
    /**
     * 异常消息（系统异常，业务异常）
     */
    public static final String MESSAGE_TYPE_ERROR="error";
    /**
     * 系统监控（分钟）
     */
    public static final String MESSAGE_TYPE_SYSTEM="system";
    /**
     * 自定义数据采集
     */
    public static final String MESSAGE_TYPE_CUSTOM_PICK="custompick";

    public MessageTree copy();

    public String getDomain();

    public String getHostName();

    public String getIpAddress();

    public String getMessage();

    public String getMessageId();
    
    public String getMessageType();

    public String getParentMessageId();

    public String getRootMessageId();

    public String getSessionToken();

    public String getThreadGroupName();

    public String getThreadId();

    public String getThreadName();
    
    public long getTimestamp();
    
    public void setTimestamp(long timestamp);

    public void setDomain(String domain);

    public void setHostName(String hostName);

    public void setIpAddress(String ipAddress);

    public void setMessage(String message);

    public void setMessageId(String messageId);
    
    public void setMessageType(String messageType);

    public void setParentMessageId(String parentMessageId);

    public void setRootMessageId(String rootMessageId);

    public void setSessionToken(String sessionToken);

    public void setThreadGroupName(String name);

    public void setThreadId(String threadId);

    public void setThreadName(String id);
}
