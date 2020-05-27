package com.storm.monitor.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.storm.monitor.server.dao.ErrorLogMapper;
import com.storm.monitor.server.service.ErrorLogService;
import com.storm.monitor.server.model.ErrorLog;
import com.storm.monitor.server.model.view.ErrorLogView;
import java.util.List;
import java.util.Map;

/**
 * Title: 【异常信息汇总表】(对应POJO对象：ErrorLog)服务实现类
 * Description: 包含对【异常信息汇总表】(ErrorLog)的增、删、改、查、构建等基本操作
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2018-03-15 10:58
 *
 */
@Service
@Transactional
public class ErrorLogServiceImpl implements ErrorLogService {
    @Autowired
    private ErrorLogMapper errorLogMapper;

    /**
    * 新增一条记录
    * @param errorLog 待插入的【ErrorLog】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int addErrorLog(ErrorLog errorLog)  throws Exception{
        return errorLogMapper.addErrorLog(errorLog);
    }
    
    @Override
    public int addErrorLogBatch(List<ErrorLog> errorLogs)  throws Exception{
        return errorLogMapper.addErrorLogBatch(errorLogs);
    }
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param errorLog 待更新的【ErrorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateErrorLog(ErrorLog errorLog)  throws Exception{
        return errorLogMapper.updateErrorLog(errorLog);
    }
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param errorLog 待更新的【ErrorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateErrorLogBySelective(ErrorLog errorLog){
        return errorLogMapper.updateErrorLogBySelective(errorLog);
    }

    /**
    * 删除一条数据库记录
    * @param errorLog 待删除的【ErrorLog】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int deleteErrorLogById(java.lang.Long id) throws Exception{
        return errorLogMapper.deleteErrorLogById(id);
    }

    /**
     * 根据条件删除数据库记录
     *
     * @param errorLogView
     * @return
     * @throws Exception
     */
    public int deleteErrorLogByCondition(ErrorLogView errorLogView) throws Exception{
        return errorLogMapper.deleteErrorLogByCondition(errorLogView);
    }
	
    /**
    * 返回查询操作的结果
    * @param errorLog errorLogView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    @Override
    public List<ErrorLog> queryErrorLogByPage(ErrorLogView errorLogView) throws Exception{
        return errorLogMapper.queryErrorLogByPage(errorLogView);
        
    }
    
    /**
    * 返回符合查询条件的记录总数
    * @param errorLogView errorLogView 查询操作的条件
    * @return 符合查询条件的记录总数
    * @throws Exception 
    */
    @Override
    public int queryErrorLogByCount(ErrorLogView errorLogView) throws Exception{
        return errorLogMapper.queryErrorLogByCount(errorLogView);
    }
    
    /**
     * 根据SQL查询
     * @param sql
     * @return
     * @throws Exception 
     */
    public List<ErrorLog> queryErrorLogBySQL(String sql) throws Exception{
        return errorLogMapper.queryErrorLogBySQL(sql);
    }
    
    /**
     * 根据SQL查询
     * @param sql
     * @return
     * @throws Exception 
     */
    public List<Map<String, Object>> queryErrorLog2MapBySQL(String sql) throws Exception{
        return errorLogMapper.queryErrorLog2MapBySQL(sql);
    }

     /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    @Override
    public ErrorLog queryErrorLogById(Object id) throws Exception{
        return errorLogMapper.queryErrorLogById(id);
    }
}
