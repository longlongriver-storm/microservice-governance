CREATE DATABASE IF NOT EXISTS storm_monitor;
use storm_monitor;

drop table if exists demo_trade_order;

drop table if exists apm_business_cfg;

drop table if exists apm_chart_confg;

drop table if exists custom_data_pick_log;

drop table if exists dao_monitor_log;

drop table if exists dao_monitor_log_day;

drop table if exists dao_monitor_log_hour;

drop table if exists disk_volume_monitor_log;

drop table if exists error_log;

drop table if exists memory_gc_monitor_log;

drop table if exists service_monitor_log;

drop table if exists service_monitor_log_day;

drop table if exists service_monitor_log_hour;

drop table if exists system_monitor_log;

/*==============================================================*/
/* Table: apm_chart_confg                                       */
/*==============================================================*/
create table apm_chart_confg
(
   id                   varchar(40) not null comment 'ID',
   chart_name           varchar(200) not null comment '图表名称',
   description          varchar(400) not null comment '图表说明',
   chart_content        text not null comment '图表内容',
   create_time          datetime not null comment '创建时间',
   modify_time          datetime not null comment '修改时间',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table apm_chart_confg comment '图表配置';

/*==============================================================*/
/* Table: custom_data_pick_log                                  */
/*==============================================================*/
create table custom_data_pick_log
(
   id                   bigint not null auto_increment comment '主键',
   log_time             datetime not null comment '日志时间',
   service_name         varchar(250) not null comment '服务名称',
   machine_address      varchar(50) not null comment '主机地址',
   trace_id             varchar(100) default ' ' comment '调用链ID',
   user_key             varchar(50) default ' ' comment '用户标识',
   pick_data            varchar(2000) default ' ' comment '抓取数据',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table custom_data_pick_log comment '自定义业务采集日志表';

/*==============================================================*/
/* Index: custom_data_pick_log_Id2                              */
/*==============================================================*/
create index custom_data_pick_log_Id2 on custom_data_pick_log
(
   log_time
);

/*==============================================================*/
/* Index: custom_data_pick_log_Id3                              */
/*==============================================================*/
create index custom_data_pick_log_Id3 on custom_data_pick_log
(
   service_name
);

/*==============================================================*/
/* Index: custom_data_pick_log_Id4                              */
/*==============================================================*/
create index custom_data_pick_log_Id4 on custom_data_pick_log
(
   machine_address
);

/*==============================================================*/
/* Index: custom_data_pick_log_Id5                              */
/*==============================================================*/
create index custom_data_pick_log_Id5 on custom_data_pick_log
(
   trace_id
);

/*==============================================================*/
/* Index: custom_data_pick_log_Id6                              */
/*==============================================================*/
create index custom_data_pick_log_Id6 on custom_data_pick_log
(
   user_key
);

/*==============================================================*/
/* Table: dao_monitor_log                                       */
/*==============================================================*/
create table dao_monitor_log
(
   log_time             datetime not null comment '日志时间',
   machine_address      varchar(50) not null comment '主机地址',
   service_name         varchar(250) not null comment '服务名称',
   success_count        bigint comment '成功次数',
   failure_count        bigint comment '失败次数',
   avg_elapsed          bigint comment '平均延时',
   max_elapsed          bigint comment '最大延时',
   min_elapsed          bigint comment '最小延时',
   lastest_error_msg    varchar(400) comment '最新错误类',
   primary key (log_time, machine_address, service_name),
   key AK_dml_2 (success_count),
   key AK_dml_3 (failure_count),
   key AK_dml_4 (avg_elapsed),
   key AK_dml_5 (max_elapsed)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table dao_monitor_log comment 'DAO访问分钟级统计表';

/*==============================================================*/
/* Table: dao_monitor_log_day                                   */
/*==============================================================*/
create table dao_monitor_log_day
(
   log_time             datetime not null comment '日志时间',
   machine_address      varchar(50) not null comment '主机地址',
   service_name         varchar(250) not null comment '服务名称',
   success_count        bigint comment '成功次数',
   failure_count        bigint comment '失败次数',
   avg_elapsed          bigint comment '平均延时',
   max_elapsed          bigint comment '最大延时',
   min_elapsed          bigint comment '最小延时',
   lastest_error_msg    varchar(400) comment '最新错误类',
   primary key (log_time, machine_address, service_name),
   key AK_dmld_2 (success_count),
   key AK_dmld_3 (failure_count),
   key AK_dmld_4 (avg_elapsed),
   key AK_dmld_5 (max_elapsed)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table dao_monitor_log_day comment 'DAO监控天汇总表';

/*==============================================================*/
/* Table: dao_monitor_log_hour                                  */
/*==============================================================*/
create table dao_monitor_log_hour
(
   log_time             datetime not null comment '日志时间',
   machine_address      varchar(50) not null comment '主机地址',
   service_name         varchar(250) not null comment '服务名称',
   success_count        bigint comment '成功次数',
   failure_count        bigint comment '失败次数',
   avg_elapsed          bigint comment '平均延时',
   max_elapsed          bigint comment '最大延时',
   min_elapsed          bigint comment '最小延时',
   lastest_error_msg    varchar(400) comment '最新错误类',
   primary key (log_time, machine_address, service_name),
   key AK_dmlh_2 (success_count),
   key AK_dmlh_3 (failure_count),
   key AK_dmlh_4 (avg_elapsed),
   key AK_dmlh_5 (max_elapsed)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table dao_monitor_log_hour comment 'DAO监控小时汇总表';

/*==============================================================*/
/* Table: disk_volume_monitor_log                               */
/*==============================================================*/
create table disk_volume_monitor_log
(
   log_time             datetime not null comment '日志时间',
   machine_address      varchar(50) not null comment '主机地址',
   volume_id            varchar(100) not null comment '磁盘卷ID',
   volume_total         bigint comment '磁盘卷总容量',
   volume_free          bigint comment '磁盘卷未分配容量',
   volume_usable        bigint comment '磁盘卷可用容量',
   primary key (log_time, machine_address, volume_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table disk_volume_monitor_log comment '磁盘监控日志表';

/*==============================================================*/
/* Table: error_log                                             */
/*==============================================================*/
create table error_log
(
   id                   bigint not null auto_increment comment '主键',
   log_time             datetime not null comment '日志时间',
   machine_address      varchar(50) not null comment '主机地址',
   service_name         varchar(250) not null comment '服务名称',
   error_type           int comment '1：业务错误，0：系统错误',
   trace_id             varchar(100) default ' ' comment '调用链ID',
   user_key             varchar(50) default ' ' comment '用户标识',
   error_code           varchar(400) default ' ' comment '错误代码',
   error_msg            varchar(4000) default ' ' comment '错误信息',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table error_log comment '异常信息汇总表';

/*==============================================================*/
/* Index: error_log_Id2                                         */
/*==============================================================*/
create index error_log_Id2 on error_log
(
   log_time
);

/*==============================================================*/
/* Index: error_log_Id3                                         */
/*==============================================================*/
create index error_log_Id3 on error_log
(
   machine_address
);

/*==============================================================*/
/* Index: error_log_Id4                                         */
/*==============================================================*/
create index error_log_Id4 on error_log
(
   service_name
);

/*==============================================================*/
/* Index: error_log_Id5                                         */
/*==============================================================*/
create index error_log_Id5 on error_log
(
   trace_id
);

/*==============================================================*/
/* Index: error_log_Id6                                         */
/*==============================================================*/
create index error_log_Id6 on error_log
(
   user_key
);

/*==============================================================*/
/* Table: memory_gc_monitor_log                                 */
/*==============================================================*/
create table memory_gc_monitor_log
(
   log_time             datetime not null comment '日志时间',
   machine_address      varchar(50) not null comment '主机地址',
   gc_name              varchar(100) not null comment 'GC名称',
   gc_count             bigint comment 'GC次数',
   gc_time              bigint comment 'GC耗时',
   primary key (log_time, machine_address, gc_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table memory_gc_monitor_log comment '内存GC监控日志表';

/*==============================================================*/
/* Table: service_monitor_log                                   */
/*==============================================================*/
create table service_monitor_log
(
   log_time             datetime not null comment '日志时间',
   machine_address      varchar(50) not null comment '主机地址',
   service_name         varchar(250) not null comment '服务名称',
   success_count        bigint comment '成功次数',
   failure_count        bigint comment '失败次数',
   avg_elapsed          bigint comment '平均延时',
   max_elapsed          bigint comment '最大延时',
   min_elapsed          bigint comment '最小延时',
   lastest_error_msg    varchar(400) DEFAULT NULL COMMENT '最新错误类',
  
   lastest_biz_error_code varchar(45) DEFAULT NULL COMMENT '最近业务错误代码',
  
   lastest_biz_error_msg varchar(400) DEFAULT NULL COMMENT '最近业务错误信息',
   biz_failure_count bigint(20) DEFAULT NULL,
   primary key (log_time, machine_address, service_name),
   key AK_sml_2 (success_count),
   key AK_sml_3 (failure_count),
   key AK_sml_4 (avg_elapsed),
   key AK_sml_5 (max_elapsed)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table service_monitor_log comment '应用服务访问分钟级统计表';

/*==============================================================*/
/* Table: service_monitor_log_day                               */
/*==============================================================*/
create table service_monitor_log_day
(
   log_time             datetime not null comment '日志时间',
   machine_address      varchar(50) not null comment '主机地址',
   service_name         varchar(250) not null comment '服务名称',
   success_count        bigint comment '成功次数',
   failure_count        bigint comment '失败次数',
   avg_elapsed          bigint comment '平均延时',
   max_elapsed          bigint comment '最大延时',
   min_elapsed          bigint comment '最小延时',
   lastest_error_msg    varchar(400) DEFAULT NULL COMMENT '最新错误类',
  
   lastest_biz_error_code varchar(45) DEFAULT NULL COMMENT '最近业务错误代码',
  
   lastest_biz_error_msg varchar(400) DEFAULT NULL COMMENT '最近业务错误信息',
   biz_failure_count bigint(20) DEFAULT NULL,
   primary key (log_time, machine_address, service_name),
   key AK_smld_2 (success_count),
   key AK_smld_3 (failure_count),
   key AK_smld_4 (avg_elapsed),
   key AK_smld_5 (max_elapsed)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table service_monitor_log_day comment '服务监控天汇总表';

/*==============================================================*/
/* Table: service_monitor_log_hour                              */
/*==============================================================*/
create table service_monitor_log_hour
(
   log_time             datetime not null comment '日志时间',
   machine_address      varchar(50) not null comment '主机地址',
   service_name         varchar(250) not null comment '服务名称',
   success_count        bigint comment '成功次数',
   failure_count        bigint comment '失败次数',
   avg_elapsed          bigint comment '平均延时',
   max_elapsed          bigint comment '最大延时',
   min_elapsed          bigint comment '最小延时',
   lastest_error_msg    varchar(400) DEFAULT NULL COMMENT '最新错误类',
  
   lastest_biz_error_code varchar(45) DEFAULT NULL COMMENT '最近业务错误代码',
  
   lastest_biz_error_msg varchar(400) DEFAULT NULL COMMENT '最近业务错误信息',
   biz_failure_count bigint(20) DEFAULT NULL,
   primary key (log_time, machine_address, service_name),
   key AK_smlh_2 (success_count),
   key AK_smlh_3 (failure_count),
   key AK_smlh_4 (avg_elapsed),
   key AK_smlh_5 (max_elapsed)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table service_monitor_log_hour comment '服务监控小时汇总表';

/*==============================================================*/
/* Table: system_monitor_log                                    */
/*==============================================================*/
create table system_monitor_log
(
   log_time             datetime not null comment '日志时间',
   machine_address      varchar(50) not null comment '主机地址',
   disk_total           bigint comment '磁盘总容量',
   disk_free            bigint comment '磁盘未分配容量',
   disk_usable          bigint comment '磁盘可用容量',
   memory_max           bigint comment '最大内存量',
   memory_total         bigint comment '内存总量',
   memory_free          bigint comment '空闲内存量',
   memory_heapUsage     bigint comment '堆内存量',
   memory_nonHeapUsage  bigint comment '非堆内存量',
   os_arch              varchar(100) comment '操作系统的架构',
   os_name              varchar(100) comment '操作系统名称',
   os_version           varchar(100) comment '操作系统的版本',
   os_availableProcessors int comment 'Java虚拟机可以使用的处理器数目',
   os_systemLoadAverage float comment '最后一分钟内系统加载平均值',
   os_totalPhysicalMemory bigint comment '总物理内存量',
   os_freePhysicalMemory bigint comment '未分配物理内存量',
   os_totalSwapSpace    bigint comment '总交换空间量',
   os_freeSwapSpace     bigint comment '未分配交换空间量',
   os_processTime       bigint comment 'java到当前为止所占用的CPU处理时间',
   os_committedVirtualMemory bigint comment 'java运行进程保证可用的虚拟内存大小',
   runtime_javaVersion  varchar(100) comment 'java版本',
   runtime_startTime    bigint comment 'JVM启动时间',
   runtime_upTime       bigint comment 'JVM运行时长',
   runtime_userDir      varchar(1000) comment '系统用户目录',
   runtime_userName     varchar(400) comment '系统用户名称',
   thread_active        int comment '活动线程数',
   thread_cat_count     int comment 'APM监控相关线程数',
   thread_count         int comment '当前总存活线程数',
   thread_daemon_count  int comment '当前守护线程数',
   thread_http          int comment '当前HTTP线程数',
   thread_peek          int comment '峰值活动线程计数',
   thread_started       int comment '已启动线程数',
   thread_total_started int comment '总启动线程数',
   primary key (log_time, machine_address),
   key AK_syml_2 (thread_peek)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table system_monitor_log comment '系统监控日志表';

/*==============================================================*/
/* Table: demo_trade_order                                      */
/*==============================================================*/
create table demo_trade_order
(
   id                   bigint not null auto_increment comment '订单ID',
   sku_name             varchar(100) not null comment '商品名称',
   sku_price            float(8,2) not null comment '商品单价',
   order_quantity       int not null comment '下单数量',
   customer_name        varchar(100) not null comment '下单客户',
   delivery_date        date not null comment '交货日期',
   delivery_place       varchar(200) not null comment '交货地点',
   order_desc           text comment '备注',
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单';

alter table demo_trade_order comment '商品订单';



CREATE TABLE `apm_business_cfg` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `business_key` varchar(200) DEFAULT NULL COMMENT '业务主键',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `class_method_name` varchar(500) DEFAULT NULL COMMENT '类名方法名',
  `parameters` varchar(1000) DEFAULT NULL COMMENT '参数JSON,格式:[{name:''token'',type:''String''},{name:''fundCode'',type:''String''},{name:''productCode'',type:''String''}]',
  `return_result` varchar(1000) DEFAULT NULL COMMENT '返回参数,格式:[{name:''investCount'',type:''int''},{name:''assets'',type:''Object''}]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_business_key` (`business_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;