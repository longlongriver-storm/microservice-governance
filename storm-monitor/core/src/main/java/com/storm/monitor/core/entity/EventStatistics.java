/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.entity;

import java.io.Serializable;

/**
 *  单项统计类
 * @author lixin
 */
public class EventStatistics implements Serializable{
	/**
	 * 成功次数
	 */
	long success;
	/**
	 * 失败次数
	 */
	long failure;
	/**
	 * 总耗时（只算成功耗时）
	 */
	long allElapsed;
	/**
	 * 最大耗时(只算成功)
	 */
	long maxElapsed;
	/**
	 * 最小耗时(只算成功)
	 */
	long minElapsed;
	/**
	 * 最新一个错误信息
	 */
	String lastestErrorMsg;
    
    
    /**
     * 业务失败次数
     */
    long bizFailure;
    /**
     * 最新一个错误失败代码
     */
    String lastestBizErrorCode;
    /**
     * 最新一个错误失败信息
     */
    String lastestBizErrorMsg;


	public EventStatistics() {
		maxElapsed=0L;
		minElapsed=Long.MAX_VALUE;
	}
	
	

	public long getSuccess() {
		return success;
	}

	public void setSuccess(long success) {
		this.success = success;
	}

	public long getFailure() {
		return failure;
	}

	public void setFailure(long failure) {
		this.failure = failure;
	}

	public long getAllElapsed() {
		return allElapsed;
	}

	public void setAllElapsed(long allElapsed) {
		this.allElapsed = allElapsed;
	}

	public long getMaxElapsed() {
		return maxElapsed;
	}

	public void setMaxElapsed(long maxElapsed) {
		this.maxElapsed = maxElapsed;
	}

	public long getMinElapsed() {
		return minElapsed;
	}

	public void setMinElapsed(long minElapsed) {
		this.minElapsed = minElapsed;
	}

	public String getLastestErrorMsg() {
		return lastestErrorMsg;
	}

	public void setLastestErrorMsg(String lastestErrorMsg) {
		this.lastestErrorMsg = lastestErrorMsg;
	}

    public long getBizFailure() {
        return bizFailure;
    }

    public void setBizFailure(long bizFailure) {
        this.bizFailure = bizFailure;
    }

    public String getLastestBizErrorCode() {
        return lastestBizErrorCode;
    }

    public void setLastestBizErrorCode(String lastestBizErrorCode) {
        this.lastestBizErrorCode = lastestBizErrorCode;
    }

    public String getLastestBizErrorMsg() {
        return lastestBizErrorMsg;
    }

    public void setLastestBizErrorMsg(String lastestBizErrorMsg) {
        this.lastestBizErrorMsg = lastestBizErrorMsg;
    }
	
	
}
