# 字段、表名注释乱码问题
alter table TABLE_PARAMS modify column PARAM_VALUE varchar(4000) character set utf8mb4 comment '表别名';
alter table COLUMNS_V2 modify column `COMMENT` varchar(256) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '表字段别名';
