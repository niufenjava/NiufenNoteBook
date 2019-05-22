/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50721
Source Host           : 127.0.0.1:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-05-22 18:05:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_common_file
-- ----------------------------
DROP TABLE IF EXISTS `t_common_file`;
CREATE TABLE `t_common_file` (
  `id` bigint(32) NOT NULL,
  `file_name` varchar(50) DEFAULT NULL COMMENT '文件名称',
  `file_url` varchar(255) DEFAULT NULL COMMENT '文件地址',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求附件表';

-- ----------------------------
-- Records of t_common_file
-- ----------------------------

-- ----------------------------
-- Table structure for t_demo_copy
-- ----------------------------
DROP TABLE IF EXISTS `t_demo_copy`;
CREATE TABLE `t_demo_copy` (
  `id` bigint(32) NOT NULL COMMENT '主键ID',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求任务表';

-- ----------------------------
-- Records of t_demo_copy
-- ----------------------------

-- ----------------------------
-- Table structure for t_require
-- ----------------------------
DROP TABLE IF EXISTS `t_require`;
CREATE TABLE `t_require` (
  `id` bigint(32) NOT NULL COMMENT '主键ID',
  `require_no` varchar(20) NOT NULL COMMENT '需求编号',
  `issue_id` bigint(32) DEFAULT NULL COMMENT '关联缺陷ID',
  `stage` tinyint(2) NOT NULL COMMENT '阶段：0-新建；1-审核阶段；2-宣讲阶段；3-开发阶段；4-测试阶段；5-上线阶段；6-验收阶段；7-完成',
  `type` tinyint(2) NOT NULL COMMENT '需求类型：1-产品需求；2-系统优化；3-问题修复',
  `topic` varchar(50) NOT NULL COMMENT '主题',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '需求主状态：1-已新建；2-已审核；3-已完成；4-已取消',
  `is_deleted` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否删除：0-否；1-是',
  `preach_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '宣讲状态：0-待宣讲；1-已宣讲',
  `dev_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '开发状态：0-待开发；1-开发中；2-开发完成；',
  `test_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '测试状态：0-待测试；1-测试中；2-预生产中；4-测试完成；',
  `release_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '上线状态：0-待上线；1-待审批；2-上线中；3-已上线',
  `accept_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '验收状态：0-待验收；1-已验收；',
  `platform_id` bigint(32) NOT NULL COMMENT '平台表主键ID',
  `system_id` bigint(32) DEFAULT NULL COMMENT '系统端表主键ID',
  `priority` tinyint(2) DEFAULT NULL COMMENT '优先级：1-紧急；2-严重；3-重要；4-一般',
  `prd_leader_id` bigint(32) DEFAULT NULL COMMENT '产品负责人ID',
  `test_leader_id` bigint(32) DEFAULT NULL COMMENT '测试负责人ID',
  `dev_leader_id` bigint(32) DEFAULT NULL COMMENT '开发负责人',
  `release_leader_id` bigint(32) DEFAULT NULL COMMENT '上线负责人ID',
  `transactor_id` bigint(32) DEFAULT NULL COMMENT '经办人ID',
  `expect_release_time` datetime DEFAULT NULL COMMENT '期望上线日期',
  `preach_time` datetime DEFAULT NULL COMMENT '宣讲日期',
  `plan_test_time` datetime DEFAULT NULL COMMENT '计划提测时间',
  `plan_release_time` datetime DEFAULT NULL COMMENT '计划上线日期',
  `release_time` datetime DEFAULT NULL COMMENT '上线时间',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求主表';

-- ----------------------------
-- Records of t_require
-- ----------------------------

-- ----------------------------
-- Table structure for t_require_change
-- ----------------------------
DROP TABLE IF EXISTS `t_require_change`;
CREATE TABLE `t_require_change` (
  `id` bigint(32) NOT NULL,
  `require_id` bigint(32) NOT NULL COMMENT '关联-需求主表ID',
  `topic` varchar(50) NOT NULL COMMENT '变更主题',
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '需求变更状态：0-已新建；1-已审核；2-已完成；3-已取消；',
  `type` tinyint(2) NOT NULL COMMENT '需求类型：1-产品需求；2-系统优化；3-问题修复',
  `platform_id` bigint(32) NOT NULL COMMENT '所属平台ID',
  `system_id` bigint(32) DEFAULT NULL COMMENT '系统端ID',
  `priority` tinyint(2) DEFAULT NULL COMMENT '系统优先级：1-紧急；2-严重；3-重要；4-一般',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求变更主表';

-- ----------------------------
-- Records of t_require_change
-- ----------------------------

-- ----------------------------
-- Table structure for t_require_change_ext
-- ----------------------------
DROP TABLE IF EXISTS `t_require_change_ext`;
CREATE TABLE `t_require_change_ext` (
  `id` bigint(32) NOT NULL,
  `change_id` bigint(32) NOT NULL COMMENT '管理-变更主表ID',
  `detail` text COMMENT '需求详情',
  `change_doc_url` varchar(255) DEFAULT NULL COMMENT '文档地址',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求扩展表';

-- ----------------------------
-- Records of t_require_change_ext
-- ----------------------------

-- ----------------------------
-- Table structure for t_require_change_file_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_require_change_file_rel`;
CREATE TABLE `t_require_change_file_rel` (
  `id` bigint(32) NOT NULL COMMENT '主键ID',
  `file_id` bigint(32) NOT NULL COMMENT '关联附件表主键ID',
  `require_id` bigint(32) NOT NULL COMMENT '关联-需求主表ID',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求变更-附件关联表';

-- ----------------------------
-- Records of t_require_change_file_rel
-- ----------------------------

-- ----------------------------
-- Table structure for t_require_change_log
-- ----------------------------
DROP TABLE IF EXISTS `t_require_change_log`;
CREATE TABLE `t_require_change_log` (
  `id` bigint(32) NOT NULL,
  `require_id` bigint(32) NOT NULL COMMENT '需求主表ID',
  `menu_id` bigint(32) NOT NULL COMMENT '系统菜单表主键ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求变更-操作记录表';

-- ----------------------------
-- Records of t_require_change_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_require_ext
-- ----------------------------
DROP TABLE IF EXISTS `t_require_ext`;
CREATE TABLE `t_require_ext` (
  `id` bigint(32) NOT NULL,
  `require_id` bigint(32) NOT NULL COMMENT '需求主表ID',
  `detail` text COMMENT '需求详情',
  `require_doc_url` varchar(255) DEFAULT NULL COMMENT '文档地址',
  `preach_issue` varchar(255) DEFAULT NULL COMMENT '宣讲遗留问题',
  `dev_preach_bak` varchar(255) DEFAULT NULL COMMENT '开发宣讲备注',
  `dev_design_doc_url` varchar(255) DEFAULT NULL COMMENT '开发设计文档地址',
  `dev_unite_doc_url` varchar(255) DEFAULT NULL COMMENT '开发联调报告地址',
  `test_doc_url` varchar(255) DEFAULT NULL COMMENT '测试报告地址',
  `pre_test_doc_url` varchar(255) DEFAULT NULL COMMENT '预生产测试报告地址',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求扩展表';

-- ----------------------------
-- Records of t_require_ext
-- ----------------------------

-- ----------------------------
-- Table structure for t_require_file_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_require_file_rel`;
CREATE TABLE `t_require_file_rel` (
  `id` bigint(32) NOT NULL COMMENT '主键ID',
  `file_id` bigint(32) NOT NULL COMMENT '关联附件表主键ID',
  `require_id` bigint(32) NOT NULL COMMENT '关联-需求主表ID',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求任务-附件关联表';

-- ----------------------------
-- Records of t_require_file_rel
-- ----------------------------

-- ----------------------------
-- Table structure for t_require_issue_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_require_issue_rel`;
CREATE TABLE `t_require_issue_rel` (
  `id` bigint(32) NOT NULL COMMENT '主键ID',
  `require_id` bigint(32) NOT NULL COMMENT '需求表主键ID',
  `issue_id` bigint(32) NOT NULL COMMENT '缺陷表主键ID',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求缺陷关联表';

-- ----------------------------
-- Records of t_require_issue_rel
-- ----------------------------

-- ----------------------------
-- Table structure for t_require_log
-- ----------------------------
DROP TABLE IF EXISTS `t_require_log`;
CREATE TABLE `t_require_log` (
  `id` bigint(32) NOT NULL,
  `require_id` bigint(32) NOT NULL COMMENT '需求主表ID',
  `menu_id` bigint(32) NOT NULL COMMENT '系统菜单表主键ID',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求任务操作记录表';

-- ----------------------------
-- Records of t_require_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_require_release
-- ----------------------------
DROP TABLE IF EXISTS `t_require_release`;
CREATE TABLE `t_require_release` (
  `id` bigint(32) NOT NULL,
  `require_id` bigint(32) DEFAULT NULL COMMENT '关联-需求主表ID',
  `system_id` bigint(32) DEFAULT NULL COMMENT '关联-系统表ID',
  `trunk_url` varchar(255) DEFAULT NULL COMMENT '代码发布地址',
  `rollback_tag_url` varchar(255) DEFAULT NULL COMMENT '回滚tag地址',
  `release_leader_id` bigint(32) DEFAULT NULL COMMENT '上线负责人ID',
  `test_leader_id` bigint(32) DEFAULT NULL COMMENT '测试负责人ID',
  `code_merge_leader_id` bigint(32) DEFAULT NULL COMMENT '次日代码合并负责人',
  `prod_verify_descp` varchar(255) DEFAULT NULL COMMENT '线上验证情况描述',
  `require_accept_descp` varchar(255) DEFAULT NULL COMMENT '需求验收说明',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求上线表';

-- ----------------------------
-- Records of t_require_release
-- ----------------------------

-- ----------------------------
-- Table structure for t_require_user_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_require_user_rel`;
CREATE TABLE `t_require_user_rel` (
  `id` bigint(32) NOT NULL,
  `require_id` bigint(32) DEFAULT NULL COMMENT '需求主表ID',
  `user_id` bigint(32) DEFAULT NULL COMMENT '用户表ID',
  `task` varchar(255) DEFAULT NULL COMMENT '相关任务',
  `content` varchar(255) DEFAULT NULL COMMENT '⁮相关内容',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求任务表';

-- ----------------------------
-- Records of t_require_user_rel
-- ----------------------------

-- ----------------------------
-- Table structure for t_test_issue
-- ----------------------------
DROP TABLE IF EXISTS `t_test_issue`;
CREATE TABLE `t_test_issue` (
  `id` bigint(32) NOT NULL COMMENT '主键ID',
  `issue_no` varchar(20) DEFAULT NULL COMMENT '问题编号',
  `require_no` bigint(32) DEFAULT NULL COMMENT '关联需求ID',
  `type` tinyint(2) DEFAULT NULL COMMENT '问题类型：待确定',
  `topic` varchar(50) NOT NULL COMMENT '问题主题',
  `env` tinyint(2) DEFAULT NULL COMMENT '环境：1-测试1；2-测试2；3-预生产；4-生产',
  `status` tinyint(2) DEFAULT '0' COMMENT '问题状态：0-已新建；1-处理中；2-已解决；3-已验证；4-已关闭；5-重新打开；',
  `result_type` tinyint(2) DEFAULT NULL COMMENT '解决状态：1-解决；2-不解决；3-重复提交；4-未完成；5-无法再次复现；6-延期；7-环境问题；8-需求变更；9-验收完成；10-已放弃',
  `priority` tinyint(2) DEFAULT NULL COMMENT '系统优先级：1-紧急；2-严重；3-重要；4-一般',
  `system_id` tinyint(2) DEFAULT NULL COMMENT '系统端ID',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `transactor_id` bigint(32) DEFAULT NULL COMMENT '经办人',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试缺陷主表';

-- ----------------------------
-- Records of t_test_issue
-- ----------------------------

-- ----------------------------
-- Table structure for t_test_issue_ext
-- ----------------------------
DROP TABLE IF EXISTS `t_test_issue_ext`;
CREATE TABLE `t_test_issue_ext` (
  `id` bigint(32) NOT NULL COMMENT '主键ID',
  `issue_id` bigint(32) NOT NULL COMMENT '关联-问题主表ID',
  `detail` text COMMENT '详情',
  `doc_url` varchar(255) DEFAULT NULL COMMENT '文档地址',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试缺陷-扩展表';

-- ----------------------------
-- Records of t_test_issue_ext
-- ----------------------------

-- ----------------------------
-- Table structure for t_test_issue_log
-- ----------------------------
DROP TABLE IF EXISTS `t_test_issue_log`;
CREATE TABLE `t_test_issue_log` (
  `id` bigint(32) NOT NULL,
  `type` tinyint(2) DEFAULT NULL COMMENT '操作类型',
  `descp` varchar(255) DEFAULT NULL COMMENT '操作描述',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `transactor_id` bigint(32) DEFAULT NULL COMMENT '当前操作人ID',
  `next_transactor_id` bigint(32) DEFAULT NULL COMMENT '下一个经办人ID',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='需求任务表';

-- ----------------------------
-- Records of t_test_issue_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_care_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_user_care_rel`;
CREATE TABLE `t_user_care_rel` (
  `id` bigint(32) NOT NULL,
  `business_id` bigint(32) NOT NULL COMMENT '业务表主键ID',
  `business_type` tinyint(2) DEFAULT NULL COMMENT '业务类型：1-需求任务；2-需求变更；3-缺陷',
  `user_id` bigint(32) DEFAULT NULL COMMENT '用户ID',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除：0-否；1-是；',
  `creator_id` bigint(32) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(32) DEFAULT NULL COMMENT '修改人ID',
  `modified_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户关注表';

-- ----------------------------
-- Records of t_user_care_rel
-- ----------------------------
