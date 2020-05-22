CREATE TABLE `t_sys_user` (
                              `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
                              `username` varchar(100) DEFAULT NULL COMMENT '登录账号',
                              `password` varchar(255) DEFAULT NULL COMMENT '密码',
                              `sex` tinyint(1) DEFAULT NULL COMMENT '性别(0-默认未知,1-男,2-女)',
                              `phone` varchar(45) DEFAULT NULL COMMENT '电话',
                              `status` tinyint(1) DEFAULT NULL COMMENT '状态(1-正常,2-冻结)',
                              `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除状态(0-正常,1-已删除)',
                              `create_user_id` varchar(32) DEFAULT NULL COMMENT '创建人',
                              `create_user_name` varchar(100) DEFAULT NULL COMMENT '创建人姓名',
                              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `update_user_id` varchar(32) DEFAULT NULL COMMENT '更新人',
                              `update_user_name` varchar(100) DEFAULT NULL COMMENT '更新人姓名',
                              `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE KEY `index_user_name` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

CREATE TABLE `t_sys_log` (
                             `id` bigint(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
                             `type` tinyint(2) DEFAULT NULL COMMENT '日志类型（1登录日志，2操作日志）',
                             `content` varchar(1000) DEFAULT NULL COMMENT '日志内容',
                             `operate_type` tinyint(2) DEFAULT NULL COMMENT '操作类型',
                             `ip` varchar(100) DEFAULT NULL COMMENT 'IP',
                             `method` varchar(500) DEFAULT NULL COMMENT '请求java方法',
                             `request_type` varchar(10) DEFAULT NULL COMMENT '请求类型',
                             `request_url` varchar(255) DEFAULT NULL COMMENT '请求路径',
                             `request_param` longtext COMMENT '请求参数',
                             `response_status` int(10) COMMENT '返回码',
                             `response_content` longtext COMMENT '返回内容',
                             `cost_time` bigint(20) DEFAULT NULL COMMENT '耗时',
                             `user_id` bigint(20) DEFAULT NULL COMMENT '操作用户账号',
                             `user_name` varchar(100) DEFAULT NULL COMMENT '操作用户名称',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统访问日志表';
