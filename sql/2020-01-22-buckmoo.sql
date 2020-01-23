/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : buckmoo

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 22/01/2020 20:00:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category_info
-- ----------------------------
DROP TABLE IF EXISTS `category_info`;
CREATE TABLE `category_info`  (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category_info
-- ----------------------------
INSERT INTO `category_info` VALUES (1, '附近兼职');
INSERT INTO `category_info` VALUES (2, '线上兼职');
INSERT INTO `category_info` VALUES (3, '高薪兼职');
INSERT INTO `category_info` VALUES (4, '福利岗位');
INSERT INTO `category_info` VALUES (5, '日结专区');
INSERT INTO `category_info` VALUES (6, '寒假兼职');
INSERT INTO `category_info` VALUES (7, '暑假兼职');
INSERT INTO `category_info` VALUES (8, '名企专区');
INSERT INTO `category_info` VALUES (9, '校园代理');
INSERT INTO `category_info` VALUES (10, '礼仪模特');
INSERT INTO `category_info` VALUES (11, '家教助教');
INSERT INTO `category_info` VALUES (12, '义工旅行');

-- ----------------------------
-- Table structure for company_info
-- ----------------------------
DROP TABLE IF EXISTS `company_info`;
CREATE TABLE `company_info`  (
  `open_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `company_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `company_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `company_owner_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `company_certificate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `company_desc` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `audit_status` tinyint(11) DEFAULT 0,
  `update_time` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`open_id`) USING BTREE,
  INDEX `companyIdx`(`company_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company_info
-- ----------------------------
INSERT INTO `company_info` VALUES ('oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', '骊山鹿鸣有限公司', '13JDE9W0D8EW9D90DWE', '杨楠', 'https://s2.ax1x.com/2020/01/05/lBDRgJ.png', '骊山鹿鸣通过优质资源的有效整合，更好服务于学生群体', 0, 1578710370915);

-- ----------------------------
-- Table structure for position_info
-- ----------------------------
DROP TABLE IF EXISTS `position_info`;
CREATE TABLE `position_info`  (
  `position_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `position_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `position_money` int(11) DEFAULT NULL,
  `position_clearing_way` tinyint(11) DEFAULT NULL,
  `position_company_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `position_time` bigint(11) DEFAULT NULL,
  `position_top` tinyint(11) DEFAULT NULL,
  `position_category` int(11) DEFAULT NULL,
  `position_desc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `position_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `position_people_num` int(11) DEFAULT NULL,
  `position_phone` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `position_browse` bigint(11) DEFAULT NULL,
  `open_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` bigint(11) DEFAULT NULL,
  `update_time` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`position_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for school_club_info
-- ----------------------------
DROP TABLE IF EXISTS `school_club_info`;
CREATE TABLE `school_club_info`  (
  `open_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `club_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `club_desc` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `school_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `owner_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `audit_status` tinyint(11) DEFAULT NULL,
  `update_time` bigint(11) DEFAULT NULL,
  `club_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for student_info
-- ----------------------------
DROP TABLE IF EXISTS `student_info`;
CREATE TABLE `student_info`  (
  `open_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `student_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `student_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `student_certificate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `student_school` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `student_resume` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `audit_status` tinyint(11) DEFAULT 0,
  `update_time` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_info
-- ----------------------------
INSERT INTO `student_info` VALUES ('oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', '41604090109', '邹长林', 'https://s2.ax1x.com/2020/01/05/lBrMPU.png', '西安工程大学', NULL, 1, 1578706346509);

-- ----------------------------
-- Table structure for student_resume
-- ----------------------------
DROP TABLE IF EXISTS `student_resume`;
CREATE TABLE `student_resume`  (
  `open_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resume_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `resume_sex` tinyint(11) DEFAULT NULL,
  `resume_age` int(11) DEFAULT NULL,
  `resume_education` tinyint(11) DEFAULT NULL,
  `resume_history` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `resume_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `resume_work` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `resume_work_category` int(11) DEFAULT NULL,
  `resume_hope_money` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `resume_about_myself` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `resume_language` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `resume_credential` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `update_time` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_resume
-- ----------------------------
INSERT INTO `student_resume` VALUES ('oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', '邹长林', 1, 22, 1, '2016年学生会副会长', '陕鼓大道58号，西安工程大学临潼校区', '作业、辅导、家教', 1, '150元/天', '我个性开放，活泼好动', 'CET4 2020.08.01获得', '2020.01.02获得辩论赛冠军', NULL);
INSERT INTO `student_resume` VALUES ('oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk2', '邹长林', 1, 22, 1, '2016年学生会副会长', '陕鼓大道58号，西安工程大学临潼校区', '作业、辅导、家教', 1, '150元/天', '我个性开放，活泼好动', 'CET4 2020.08.01获得', '2020.01.02获得辩论赛冠军', NULL);

-- ----------------------------
-- Table structure for system_settings
-- ----------------------------
DROP TABLE IF EXISTS `system_settings`;
CREATE TABLE `system_settings`  (
  `system_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `system_value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `system_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`system_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_settings
-- ----------------------------
INSERT INTO `system_settings` VALUES ('root_id', '15291418231', '管理员账户');
INSERT INTO `system_settings` VALUES ('root_pwd', 'lhl123456an+', '管理员密码');

-- ----------------------------
-- Table structure for user_basic_info
-- ----------------------------
DROP TABLE IF EXISTS `user_basic_info`;
CREATE TABLE `user_basic_info`  (
  `open_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_city` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_icon` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_grade` int(11) UNSIGNED DEFAULT 0,
  `user_sex` tinyint(11) DEFAULT NULL,
  PRIMARY KEY (`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_basic_info
-- ----------------------------
INSERT INTO `user_basic_info` VALUES ('oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', 'Tim', '泽西岛', '15291418231', 'http://thirdwx.qlogo.cn/mmopen/vi_32/bxVEQxwmOLibgHtYurJxvW0yicXLVcTCUiaDQDqibEyoIKwS7ZRdOsZL02RibF79vdNt6GgFKMr4fuDNV8T7X3ficTfg/132', 0, 1);

SET FOREIGN_KEY_CHECKS = 1;
