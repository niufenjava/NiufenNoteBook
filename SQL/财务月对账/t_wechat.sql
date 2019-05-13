/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50721
Source Host           : 127.0.0.1:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-09-08 15:09:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_wechat
-- ----------------------------
DROP TABLE IF EXISTS `t_wechat`;
CREATE TABLE `t_wechat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pay_date` varchar(255) DEFAULT NULL,
  `pay_no` varchar(255) DEFAULT NULL,
  `channel` varchar(255) DEFAULT NULL,
  `pay_type` varchar(255) DEFAULT NULL,
  `pay_money` varchar(255) DEFAULT NULL,
  `ret_money` varchar(255) DEFAULT NULL,
  `ret_no` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_wechat_pay_no` (`pay_no`) USING BTREE,
  KEY `t_wechat_ret_no` (`ret_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
