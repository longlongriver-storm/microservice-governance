package com.storm.monitor.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.storm.monitor.server.dao.DiskVolumeMonitorLogMapper;
import com.storm.monitor.server.service.DiskVolumeMonitorLogService;
import com.storm.monitor.server.model.DiskVolumeMonitorLog;
import com.storm.monitor.server.model.view.DiskVolumeMonitorLogView;
import java.util.List;

/**
 * Title: 【磁盘监控日志表】(对应POJO对象：DiskVolumeMonitorLog)服务实现类
 * Description: 包含对【磁盘监控日志表】(DiskVolumeMonitorLog)的增、删、改、查、构建等基本操作
 * Copyriht: Copyright (c) 2017
 * Company: Storm Workshop
 * @author lixin
 * @version 1.0 Date: 2017-07-24 15:22
 *
 */
@Service
@Transactional
public class DiskVolumeMonitorLogServiceImpl implements DiskVolumeMonitorLogService {
    @Autowired
    private DiskVolumeMonitorLogMapper diskVolumeMonitorLogMapper;

    /**
    * 新增一条记录
    * @param diskVolumeMonitorLog 待插入的【DiskVolumeMonitorLog】实体
    * @return insert操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int addDiskVolumeMonitorLog(DiskVolumeMonitorLog diskVolumeMonitorLog)  throws Exception{
        return diskVolumeMonitorLogMapper.addDiskVolumeMonitorLog(diskVolumeMonitorLog);
    }
    
    @Override
    public int addDiskVolumeMonitorLogBatch(List<DiskVolumeMonitorLog> diskVolumeMonitorLogs)  throws Exception{
        return diskVolumeMonitorLogMapper.addDiskVolumeMonitorLogBatch(diskVolumeMonitorLogs);
    }
	
    /**
    * 更新一条数据库记录（全部字段）
    * @param diskVolumeMonitorLog 待更新的【DiskVolumeMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateDiskVolumeMonitorLog(DiskVolumeMonitorLog diskVolumeMonitorLog)  throws Exception{
        return diskVolumeMonitorLogMapper.updateDiskVolumeMonitorLog(diskVolumeMonitorLog);
    }
	
    /**
    * 更新一条数据库记录（部分字段）
    * @param diskVolumeMonitorLog 待更新的【DiskVolumeMonitorLog】实体
    * @return update操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int updateDiskVolumeMonitorLogBySelective(DiskVolumeMonitorLog diskVolumeMonitorLog){
        return diskVolumeMonitorLogMapper.updateDiskVolumeMonitorLogBySelective(diskVolumeMonitorLog);
    }

    /**
    * 删除一条数据库记录
    * @param diskVolumeMonitorLog 待删除的【DiskVolumeMonitorLog】实体
    * @return delete操作影响的数据库条数
    * @throws Exception 
    */
    @Override
    public int deleteDiskVolumeMonitorLogById(java.util.Date  logTime) throws Exception{
        return diskVolumeMonitorLogMapper.deleteDiskVolumeMonitorLogById(logTime);
    }

    /**
     * 根据条件删除数据库记录
     *
     * @param diskVolumeMonitorLogView
     * @return
     * @throws Exception
     */
    public int deleteDiskVolumeMonitorLogByCondition(DiskVolumeMonitorLogView diskVolumeMonitorLogView) throws Exception{
        return diskVolumeMonitorLogMapper.deleteDiskVolumeMonitorLogByCondition(diskVolumeMonitorLogView);
    }
	
    /**
    * 返回查询操作的结果
    * @param diskVolumeMonitorLog diskVolumeMonitorLogView 查询操作的条件
    * @return 符合查询条件的记录
    * @throws Exception 
    */
    @Override
    public List<DiskVolumeMonitorLog> queryDiskVolumeMonitorLogByPage(DiskVolumeMonitorLogView diskVolumeMonitorLogView) throws Exception{
        return diskVolumeMonitorLogMapper.queryDiskVolumeMonitorLogByPage(diskVolumeMonitorLogView);
        
    }

     /**
    * 根据主键查询记录
    * @param id  主键ID
    * @return 一条数据库记录
    * @throws Exception 
    */
    @Override
    public DiskVolumeMonitorLog queryDiskVolumeMonitorLogById(Object id) throws Exception{
        return diskVolumeMonitorLogMapper.queryDiskVolumeMonitorLogById(id);
    }
}
