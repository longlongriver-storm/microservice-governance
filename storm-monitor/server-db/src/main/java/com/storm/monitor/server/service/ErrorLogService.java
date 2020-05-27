package com.storm.monitor.server.service;

import com.storm.monitor.server.model.ErrorLog;
import com.storm.monitor.server.model.view.ErrorLogView;

import java.util.List;
import java.util.Map;

/**
 *
 * Title: 【异常信息汇总表】(对应POJO对象：ErrorLog)服务接口类
 * Description: 异常信息汇总表管理接口类
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2018-03-15 10:58
 *
 */
public interface ErrorLogService{
    /**
    * 新增一条记录
    * @param errorLog 待插入的【ErrorLog】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    public int addErrorLog(ErrorLog errorLog)  throws Exception;
    
    public int addErrorLogBatch(List<ErrorLog> errorLogs)  throws Exception;
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param errorLog 待更新的【ErrorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateErrorLog(ErrorLog errorLog)  throws Exception;
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param errorLog 待更新的【ErrorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    public int updateErrorLogBySelective(ErrorLog errorLog);

    /**
    * 删除一条数据库记录
    * @param errorLog 待删除的【ErrorLog】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    public int deleteErrorLogById(java.lang.Long id) throws Exception;
	
    /**
     * 根据条件删除数据库记录
     *
     * @param errorLogView
     * @return
     * @throws Exception
     */
    public int deleteErrorLogByCondition(ErrorLogView errorLogView) throws Exception;
    
    /**
    * 返回查询操作的结果
    * @param errorLog errorLogView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    public List<ErrorLog> queryErrorLogByPage(ErrorLogView errorLogView)throws Exception;
    
    /**
    * 返回查询操作的总数
    * @param errorLogView errorLogView 查询操作的条件
    * @return 符合查询条件的记录总数
    * @throws Exception 
    */
    public int queryErrorLogByCount(ErrorLogView errorLogView)throws Exception;
    
    /**
     * 根据SQL查询
     * @param sql
     * @return
     * @throws Exception 
     */
    public List<ErrorLog> queryErrorLogBySQL(String sql) throws Exception;
    
    /**
     * 根据SQL查询
     * @param sql
     * @return
     * @throws Exception 
     */
    public List<Map<String, Object>> queryErrorLog2MapBySQL(String sql) throws Exception;
	
    /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    public ErrorLog queryErrorLogById(Object id) throws Exception;
    
}