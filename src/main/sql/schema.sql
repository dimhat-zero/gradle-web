CREATE DATABASE seckill;
use seckill;

--创建表
CREATE TABLE seckill(
`seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
`name` varchar(120) NOT NULL COMMENT '商品名称',
`number` int NOT NULL COMMENT '库存数量',
`start_time` timestamp NOT NULL COMMENT '秒杀开始时间',
`end_time` timestamp NOT NULL COMMENT '秒杀结束时间',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
PRIMARY KEY(seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT '秒杀库存表'

--初始化数据
insert into
    seckill(name,number,start_time,end_time)
values
    ('秒杀iphone6',100,'2016-09-07 00:00:00','2016-10-07 00:00:00'),
    ('秒杀ipad2',50,'2016-09-07 00:00:00','2016-10-07 00:00:00'),
    ('秒杀小米',200,'2016-09-07 00:00:00','2016-10-07 00:00:00'),
    ('秒杀红米note',300,'2016-09-07 00:00:00','2016-10-07 00:00:00')

--订单表
create table success_killed(
`seckill_id` bigint NOT NULL COMMENT '秒杀商品id',
`user_phone` bigint NOT NULL COMMENT '用户手机号',
`state` tinyint NOT NULL DEFAULT -1 COMMENT '-1无效 0成功 1已付款',
`create_time` timestamp NOT NULL COMMENT '创建时间',
PRIMARY KEY(seckill_id,user_phone),/*联合主键*/
key idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '秒杀成功明细表'

--手写ddl，版本更新可控