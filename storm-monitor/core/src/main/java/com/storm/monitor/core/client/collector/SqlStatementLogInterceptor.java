/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storm.monitor.core.client.collector;

import com.storm.monitor.core.client.Bee;
import com.storm.monitor.core.entity.EventMessage;
import java.util.Properties;
import org.apache.ibatis.cache.CacheKey;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 重点挡截Executor
 * @author lixin
 */
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),  
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,CacheKey.class, BoundSql.class})
})
public class SqlStatementLogInterceptor implements Interceptor { 

	Logger log = LoggerFactory.getLogger(SqlStatementLogInterceptor.class);

	@SuppressWarnings("unused")
	private Properties properties;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		String sqlId = mappedStatement.getId();

		Object returnValue = null;
		EventMessage eventMessage = null;
		try{
			long t1 = System.currentTimeMillis();
			returnValue = invocation.proceed();
			//记录正常调用事件
			eventMessage = new EventMessage(null, sqlId, EventMessage.TYPE_DAO_METHOD, System.currentTimeMillis() - t1);
		}catch(Exception ex){
			//记录异常调用事件
			eventMessage = new EventMessage(null, sqlId, EventMessage.TYPE_DAO_METHOD, null);
			eventMessage.LogError(ex);
			throw ex;// 异常接着往上抛
		}finally{
			Bee.pickEventMessage(eventMessage);
		}

		return returnValue;
	}

	@Override
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	@Override
	public void setProperties(Properties arg0) {
		this.properties = arg0;
	}
}
