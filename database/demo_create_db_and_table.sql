CREATE DATABASE IF NOT EXISTS storm_demo;
use storm_demo;
drop table if exists demo_trade_order;

/*==============================================================*/
/* Table: demo_trade_order                                      */
/*==============================================================*/
create table demo_trade_order
(
   id                   bigint not null comment '订单ID',
   sku_name             varchar(100) not null comment '商品名称',
   sku_price            float(8,2) not null comment '商品单价',
   order_quantity       int not null comment '下单数量',
   customer_name        varchar(100) not null comment '下单客户',
   delivery_date        date not null comment '交货日期',
   delivery_place       varchar(200) not null comment '交货地点',
   order_desc           text comment '备注',
   primary key (id)
);

alter table demo_trade_order comment '商品订单';
