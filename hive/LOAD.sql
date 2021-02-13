drop table atlas_demo.t_order_temp;

CREATE TABLE atlas_demo.t_order_temp
(
    id                 bigint COMMENT '订单ID',
    order_no string COMMENT '订单号',
    biz_center_order_no string COMMENT '订单中心订单号',
    member_id          bigint COMMENT '会员ID',
    member_no string COMMENT '会员编号',
    member_phone string COMMENT '会员手机号',
    store_id           bigint COMMENT '站点ID',
    city_code string COMMENT '城市code',
    origin             bigint COMMENT '订单来源，参见枚举',
    type               bigint COMMENT '订单类型',
    status             bigint COMMENT '订单状态，参见枚举',
    service_start_time date COMMENT '服务开始时间',
    service_end_time   date COMMENT '服务结束时间',
    create_time        date COMMENT '订单创建时间',
    modify_time        date COMMENT '订单修改时间',
    cancel_time        date COMMENT '订单取消时间',
    pay_time           date COMMENT '订单支付时间',
    finish_time        date COMMENT '订单完成时间',
    create_type        bigint COMMENT '创建人类型,1系统，2用户，3员工，参见枚举',
    create_emp         bigint COMMENT '创建人ID',
    modify_type        bigint COMMENT '修改人类型,1系统，2用户，3员工，参见枚举',
    modify_emp         bigint COMMENT '修改人ID',
    store_no string COMMENT '站点编号',
    expect_start_time  date COMMENT '预计开始时间',
    expect_end_time    date COMMENT '预计结束时间',
    city_name string COMMENT '城市名称',
    store_name string COMMENT '网点名称',
    member_nick_name string COMMENT '会员昵称'
) COMMENT '订单基础信息临时表' partitioned by (dt string)
  ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
STORED AS textfile;


LOAD DATA INPATH '/user/haijun.zhang/export_result.txt' OVERWRITE INTO TABLE atlas_demo.t_order_temp;
insert overwrite table atlas_demo.t_order select * from atlas_demo.t_order_temp;
