/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.entity;

import com.storm.monitor.core.util.MilliSecondTimer;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 事件消息
 *
 * @author lixin
 */
public class EventMessage {

	/**
	 * 类型：服务方法
	 */
	public static final short TYPE_SERVICE_METHOD = 0;
	/**
	 * 类型：数据访问DAO方法
	 */
	public static final short TYPE_DAO_METHOD = 1;
	/**
	 * 调用状态：成功
	 */
	public static final short CALL_STATUS_SUCCESS = 0;
	/**
	 * 调用状态：失败
	 */
	public static final short CALL_STATUS_ERROR = -1;
    /**
     * 调用链跟踪ID
     */
    String traceId;
    /**
     * 用户标识，可以是用户ID，也可以是手机号（倒过来写）
     */
    String userKey;

	//消息启动当前毫秒数
	long timeInMillis;
	/**
	 * 类名
	 */
	String clazzName;
	/**
	 * 方法名
	 */
	String methodName;
	/**
	 * 消息类型:0服务方法；1数据DAO
	 */
	short type;
	/**
	 * 调用状态：0成功；-1失败
	 */
	short callStatus;
	/**
	 * 调用耗时
	 */
	long elapsed;
	/**
	 * 事件消息
	 */
	String message;
	/**
	 * 错误类型
	 */
	String errorType;
	/**
	 * 错误消息
	 */
	String errorMessage;
	/**
	 * 错误消息堆栈
	 */
	String errorStack;
    
    /**
     * 业务事件
     */
    BizMessage bizMessage;
	/**
	 * 附加信息,键值对
	 */
	StringBuilder infos;

	public EventMessage(String clazzName, String methodName, short type) {
		this.timeInMillis = MilliSecondTimer.currentTimeMillis();
		this.clazzName = clazzName;
		this.methodName = methodName;
		this.type = type;
	}

	public EventMessage(String clazzName, String methodName, short type, long elapsed) {
		this.timeInMillis = MilliSecondTimer.currentTimeMillis();
		this.clazzName = clazzName;
		this.methodName = methodName;
		this.type = type;
		this.elapsed = elapsed;
	}

	public EventMessage(String clazzName, String methodName, short type, long elapsed, String message) {
		this.clazzName = clazzName;
		this.methodName = methodName;
		this.type = type;
		this.elapsed = elapsed;
		this.message = message;
	}
	
	
	
	

	public EventMessage(String clazzName, String methodName, short type, String message) {
		this.timeInMillis = MilliSecondTimer.currentTimeMillis();
		this.clazzName = clazzName;
		this.methodName = methodName;
		this.type = type;
		this.message = message;
	}

	/**
	 * 记录错误堆栈、类型
	 *
	 * @param cause
	 */
	public void LogError(Throwable cause) {
		this.callStatus = CALL_STATUS_ERROR;
		if (cause == null) {
			return;
		}
        this.errorType = cause.getClass().getName();
		StringWriter writer = new StringWriter(1024);   //1024
		cause.printStackTrace(new PrintWriter(writer));
        if((writer.getBuffer().length()+this.errorType.length())>3950){   //最大长度不能超过3500个字符，否则超出数据库字段的长度
            this.errorStack=writer.getBuffer().substring(0, 3950-this.errorType.length());
        }else{
            this.errorStack=writer.getBuffer().toString();
        }
		//this.errorStack = writer.getBuffer().substring(0, 102400);   //writer.toString();
		
	}

	/**
	 * 记录错误信息
	 *
	 * @param errorMessage
	 */
	public void LogError(String errorMessage) {
		this.callStatus = CALL_STATUS_ERROR;
		this.errorMessage = errorMessage;
	}
    
	/**
	 * 记录错误信息、类型、堆栈
	 *
	 * @param errorMessage
	 * @param cause
	 */
	public void LogError(String errorMessage, Throwable cause) {
		this.callStatus = CALL_STATUS_ERROR;
		this.errorMessage = errorMessage;
		LogError(cause);
	}

	/**
	 * 添加一个附加信息
	 *
	 * @param infoKey
	 * @param infoValue
	 */
	public void addInfo(String infoKey, Object infoValue) {
		if (infos == null) {
			infos = new StringBuilder();
            infos.append(infoKey).append("=").append(infoValue);
		}else{
            infos.append(";;;").append(infoKey).append("=").append(infoValue);
        }
		//infos.put(infoKey, infoValue);
	}

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

	public long getTimeInMillis() {
		return timeInMillis;
	}

	public String getClazzName() {
		return clazzName;
	}

	public String getMethodName() {
		return methodName;
	}

	public short getType() {
		return type;
	}

	public long getElapsed() {
		return elapsed;
	}

	public short getCallStatus() {
		return callStatus;
	}

	public String getMessage() {
		return message;
	}

	public String getErrorType() {
		return errorType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorStack() {
		return errorStack;
	}

	public StringBuilder getInfos() {
		return infos;
	}

    public BizMessage getBizMessage() {
        return bizMessage;
    }

    public void setBizMessage(BizMessage bizMessage) {
        this.bizMessage = bizMessage;
    }
}
