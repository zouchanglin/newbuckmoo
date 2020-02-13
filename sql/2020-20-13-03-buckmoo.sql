/*
 Navicat Premium Data Transfer

 Source Server         : ProductEvn
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : lslm.live:3306
 Source Schema         : buckmoo

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 13/02/2020 19:43:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for apply_position
-- ----------------------------
DROP TABLE IF EXISTS `apply_position`;
CREATE TABLE `apply_position`  (
  `apply_id` int(11) NOT NULL AUTO_INCREMENT,
  `position_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `open_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `read_status` tinyint(11) DEFAULT NULL,
  `create_time` bigint(11) DEFAULT NULL,
  `update_time` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`apply_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of apply_position
-- ----------------------------
INSERT INTO `apply_position` VALUES (1, '1580022960792934855', 'oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', 0, 1581329193004, 1581329193004);
INSERT INTO `apply_position` VALUES (2, '1580022960792934855', 'oxrwq0xrKKyqiAGE8O9TM3L1yaQY', 0, 1581330572822, 1581330572822);
INSERT INTO `apply_position` VALUES (3, '1580022960792934856', 'oxrwq0xrKKyqiAGE8O9TM3L1yaQY', 0, 1581331256126, 1581331256126);
INSERT INTO `apply_position` VALUES (4, '1580022960792934857', 'oxrwq0xrKKyqiAGE8O9TM3L1yaQY', 0, 1581331369480, 1581331369480);
INSERT INTO `apply_position` VALUES (5, '1580022960792934862', 'oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', 0, 1581384825978, 1581384825978);
INSERT INTO `apply_position` VALUES (6, '1580022960792934858', 'oxrwq0xrKKyqiAGE8O9TM3L1yaQY', 0, 1581472840346, 1581472840346);
INSERT INTO `apply_position` VALUES (7, '1580022960792934861', 'oxrwq0xrKKyqiAGE8O9TM3L1yaQY', 0, 1581590324259, 1581590324259);
INSERT INTO `apply_position` VALUES (8, '1580022960792934862', 'oxrwq0xrKKyqiAGE8O9TM3L1yaQY', 0, 1581590329823, 1581590329823);

-- ----------------------------
-- Table structure for audit_mark
-- ----------------------------
DROP TABLE IF EXISTS `audit_mark`;
CREATE TABLE `audit_mark`  (
  `open_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `student_mark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `club_mark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `company_mark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `audit_stu_time` bigint(11) DEFAULT NULL,
  `audit_company_time` bigint(11) DEFAULT NULL,
  `audit_club_time` bigint(11) DEFAULT NULL,
  `audit_stu_count` int(11) DEFAULT NULL,
  `audit_club_count` int(11) DEFAULT NULL,
  `audit_company_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of audit_mark
-- ----------------------------
INSERT INTO `audit_mark` VALUES ('oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', '去除自动填充', '信息很完整AAA', '邹长林，你好', 1581421484700, 1581400841377, 1581402763332, 4, 1, 3);

-- ----------------------------
-- Table structure for category_info
-- ----------------------------
DROP TABLE IF EXISTS `category_info`;
CREATE TABLE `category_info`  (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `company_info` VALUES ('oxrwq0xrKKyqiAGE8O9TM3L1yaQY', '我', '我', '我', 'http://lslm.live:8090/fileserver/file/fileDownload?fileUrl=0fa1f5f7-9a0a-4f38-a33c-e43148ee8799.jpeg', '我', 1, 1581331323719);
INSERT INTO `company_info` VALUES ('oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', '骊山鹿鸣有限公司', '13JDE9W0D8EW9D90DWE', '杨楠', 'https://s2.ax1x.com/2020/01/05/lBDRgJ.png', '骊山鹿鸣通过优质资源的有效整合，更好服务于学生群体', 1, 1581319198849);

-- ----------------------------
-- Table structure for general_order
-- ----------------------------
DROP TABLE IF EXISTS `general_order`;
CREATE TABLE `general_order`  (
  `order_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `order_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `order_type` tinyint(11) DEFAULT NULL,
  `order_money` decimal(11, 2) DEFAULT NULL,
  `order_open_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `order_pay_status` tinyint(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for long_text_storage
-- ----------------------------
DROP TABLE IF EXISTS `long_text_storage`;
CREATE TABLE `long_text_storage`  (
  `args_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `args_text` text CHARACTER SET utf8 COLLATE utf8_general_ci,
  PRIMARY KEY (`args_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of long_text_storage
-- ----------------------------
INSERT INTO `long_text_storage` VALUES ('service_agree', '骊山鹿鸣客户服务协议\r\n\r\n特别提示：\r\n在使用骊山鹿鸣提供的各项服务之前，您应当认真阅读并遵守《骊山鹿鸣客户服务协议》（以下简称“本协议”），请您务必审慎阅读、充分理解各条款内容，特别是免除或者限制责任的条款、争议解决条款。免除或者限制责任的条款可能会以加粗字体显示，您应重点阅读。如您对协议有任何疑问，应向骊山鹿鸣客服咨询。\r\n\r\n当您按照登录页面提示填写信息、阅读并同意本协议且完成全部登录程序后，或您以其他骊山鹿鸣允许的方式实际使用骊山鹿鸣的服务时，即表示您已充分阅读、理解并接受本协议的全部内容，并与骊山鹿鸣教育科技有限公司达成协议。您承诺接受并遵守本协议的约定，届时您不应以未阅读本协议的内容对您问询的解答等理由，主张本协议无效，或要求撤销本协议。');

-- ----------------------------
-- Table structure for position_info
-- ----------------------------
DROP TABLE IF EXISTS `position_info`;
CREATE TABLE `position_info`  (
  `position_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `position_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `position_money` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `position_clearing_way` tinyint(11) DEFAULT NULL,
  `position_company_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `position_top` tinyint(11) DEFAULT NULL,
  `position_category` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `position_desc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `position_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `position_people_num` int(11) DEFAULT NULL,
  `position_phone` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `position_browse` bigint(11) DEFAULT NULL,
  `open_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` bigint(11) DEFAULT NULL,
  `update_time` bigint(11) DEFAULT NULL,
  `audit_status` tinyint(11) DEFAULT NULL,
  `audit_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`position_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of position_info
-- ----------------------------
INSERT INTO `position_info` VALUES ('1580022960792934855', '周末影院兼职', '600元/天', 0, '13JDE9W0D8EW9D90DWE', 1, '1#3#7#9#', '周末影院兼职,负责检验票据等简单的工作', '临潼太平洋影城3层7号厅', 12, '15291418231', 122, 'oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', 1580022960791, 1580728960792, 1, '信息很完整，AAA');
INSERT INTO `position_info` VALUES ('1580022960792934856', '周末影院兼职2', '600元/天', 0, '13JDE9W0D8EW9D90DWE', 1, '1#2#', '周末影院兼职,负责检验票据等简单的工作', '临潼太平洋影城3层7号厅', 12, '15291418231', 29, 'oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', 1580022960791, 1580728960792, 1, '信息很完整，AAA');
INSERT INTO `position_info` VALUES ('1580022960792934857', '周末影院兼职3', '600元/天', 0, '13JDE9W0D8EW9D90DWE', 1, '4#5#6#', '周末影院兼职,负责检验票据等简单的工作', '临潼太平洋影城3层7号厅', 12, '15291418231', 17, 'oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', 1580022960791, 1580728960792, 1, '信息很完整，AAA');
INSERT INTO `position_info` VALUES ('1580022960792934858', '周末影院兼职4', '600元/天', 0, '13JDE9W0D8EW9D90DWE', 1, '3#9#10#', '周末影院兼职,负责检验票据等简单的工作', '临潼太平洋影城3层7号厅', 12, '15291418231', 7, 'oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', 1580022960791, 1580728960792, 1, '信息很完整，AAA');
INSERT INTO `position_info` VALUES ('1580022960792934859', '周末影院兼职5', '600元/天', 0, '13JDE9W0D8EW9D90DWE', 1, '4#5#', '周末影院兼职,负责检验票据等简单的工作', '临潼太平洋影城3层7号厅', 12, '15291418231', 8, 'oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', 1580022960791, 1580728960792, 1, '信息很完整，AAA');
INSERT INTO `position_info` VALUES ('1580022960792934861', '周末影院兼职6', '600元/天', 0, '13JDE9W0D8EW9D90DWE', 1, '3#', '周末影院兼职,负责检验票据等简单的工作', '临潼太平洋影城3层7号厅', 12, '15291418231', 4, 'oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', 1580022960791, 1580728960792, 1, '信息很完整，AAA');
INSERT INTO `position_info` VALUES ('1580022960792934862', '周末影院兼职7', '600元/天', 0, '13JDE9W0D8EW9D90DWE', 1, '1#3#5#10#', '周末影院兼职,负责检验票据等简单的工作', '临潼太平洋影城3层7号厅', 12, '15291418231', 5, 'oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', 1580022960791, 1580728960792, 1, '信息很完整，AAA');

-- ----------------------------
-- Table structure for recommend_sign
-- ----------------------------
DROP TABLE IF EXISTS `recommend_sign`;
CREATE TABLE `recommend_sign`  (
  `recommend_id` int(11) NOT NULL AUTO_INCREMENT,
  `sign_open_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '被推荐人openID',
  `push_open_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '推荐人openID',
  `recommend_type` tinyint(11) DEFAULT NULL COMMENT '推荐类型：0、学生推荐学生 1、社团推荐社团 2、企业推荐企业',
  PRIMARY KEY (`recommend_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  PRIMARY KEY (`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of school_club_info
-- ----------------------------
INSERT INTO `school_club_info` VALUES ('oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', '读书奋进会', '读书', '西安工程大学', '张三', 1, 1581319613413);

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
INSERT INTO `student_info` VALUES ('oxrwq0xrKKyqiAGE8O9TM3L1yaQY', '41709310119', '刘景亮', 'http://img.zouchanglin.cn///20200210/Qlag5WQLDS3C.png', '西安工程大学', NULL, 1, 1581081276401);
INSERT INTO `student_info` VALUES ('oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', '41604090109', '邹长林', 'https://s2.ax1x.com/2020/01/05/lBrMPU.png', '西安工程大学', NULL, 1, 1581404799004);

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
INSERT INTO `student_resume` VALUES ('oxrwq0xrKKyqiAGE8O9TM3L1yaQY', 'ssss', 1, 12, 1, 'xxx', 'xxx', 'xx', 1, 'xx', 'x', 'xx', 'xx', 1581592047234);
INSERT INTO `student_resume` VALUES ('oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', '1', 1, 12, 3, '哦', '哦', '哦', 1, '4999', '你你你', '6', '646', 1581384877790);
INSERT INTO `student_resume` VALUES ('oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk2', '邹长林', 1, 22, 1, '2016年学生会副会长', '陕鼓大道58号，西安工程大学临潼校区', '作业、辅导、家教', 1, '150元/天', '我个性开放，活泼好动', 'CET4 2020.08.01获得', '2020.01.02获得辩论赛冠军', 1580904230186);

-- ----------------------------
-- Table structure for system_settings
-- ----------------------------
DROP TABLE IF EXISTS `system_settings`;
CREATE TABLE `system_settings`  (
  `system_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `system_value` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `system_remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`system_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_settings
-- ----------------------------
INSERT INTO `system_settings` VALUES ('admin_open_id', 'oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk#', '管理员openid');
INSERT INTO `system_settings` VALUES ('new_club', '0', '社团注册赠送积分');
INSERT INTO `system_settings` VALUES ('new_company', '100', '企业注册赠送积分');
INSERT INTO `system_settings` VALUES ('new_student', '25', '学生注册赠送积分');
INSERT INTO `system_settings` VALUES ('recommend_club', '50', '社团推荐获得积分');
INSERT INTO `system_settings` VALUES ('recommend_company', '50', '企业推荐获得积分');
INSERT INTO `system_settings` VALUES ('recommend_student', '25', '学生推荐获得积分');
INSERT INTO `system_settings` VALUES ('root_id', '15291418231', '管理员账户');
INSERT INTO `system_settings` VALUES ('root_pwd', '15291418231', '管理员密码');

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
INSERT INTO `user_basic_info` VALUES ('oxrwq0-8SJ-dqHCHmRgdZGECm6lI', 'On a comfort', '中国德州', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/fdia0HSJNU74QobjGWrqNPdIvvEEJSptuKylzbpUmlF1xHEUPV3o6rVtL72GgXPHRrNaspkLDqOzcfVSA6qxiadg/132', 0, 2);
INSERT INTO `user_basic_info` VALUES ('oxrwq002wnt7r_cogFqGF7A-5JVo', '琛琛琛', '中国渭南', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/XebicM9vicxIINn4Zt4aww4aG5tt5Hwd5yuH4KBeoGicDhsL16FZAoOVW8kQmyKicXjLHbRPfibziaTc2kL74qcGGicNA/132', 0, 2);
INSERT INTO `user_basic_info` VALUES ('oxrwq00fHEeEBWkVAIYZKg_BPE8A', '痞猫', '中国宝鸡', '13369205518', 'http://thirdwx.qlogo.cn/mmopen/vi_32/Bk69C6tH6LAoM7WFAe1uUF6CtxWUSE0lhP6POYv0SFL06NVTV3z8cHKMRghwKJPXnbAHpicNCaArTbSYqN8hPMQ/132', 0, 2);
INSERT INTO `user_basic_info` VALUES ('oxrwq02nIOZ-S-VSpxP5M2j09wsE', '骊山鹿鸣-杨楠', '中国西安', '17795633226', 'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIvO0AGP9hMEN6jmeKtan6M9adKdBl2PxEia6DSzPCk6dVOffNwf3WPNgsm3ClNqenIFvyhXulk14Q/132', 0, 1);
INSERT INTO `user_basic_info` VALUES ('oxrwq02VjjCvsXPAUMRAy_DVSLL8', 'LYIUMI', '中国深圳', '13510270870', 'http://thirdwx.qlogo.cn/mmopen/vi_32/MibOYDxw9TDxz9pf0T88cDAqlKsb1avibUP3S4ib40noMDhcYG7UbaIJc8U4zfb0MY0FjGia9UFxMRKOx19ZGAE60w/132', 0, 2);
INSERT INTO `user_basic_info` VALUES ('oxrwq03i9ruuLFvkzcuzzG1WKiU4', '囍', '中国咸阳', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/de957f8BYtoJOednBMSrGUgdmrmXX4r0AKBcIoIbaW1zeI9ibNmic0QNeGVbpmf1F2TyVAhgGYKNNeiad5X8w9FicA/132', 0, 1);
INSERT INTO `user_basic_info` VALUES ('oxrwq03XppzvHWqHVfyWWmt5l2ZE', '孔乙己', '中国商洛', '13299179065', 'http://thirdwx.qlogo.cn/mmopen/vi_32/aKhm8wm0kgUnuY7KrYrAO0IENlw5wT7JGTdbCJRIxy7rZmfJ9GCHpF4Vz4Hdj77oYLdl0wwZNmhYOibiarS2X6Tw/132', 0, 1);
INSERT INTO `user_basic_info` VALUES ('oxrwq04jSyxy41xumzJZ0SZORh-w', '兰兮', '', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/FoE5JTn2hm9ibNxNIxEYSTPpn3ibnia4kUDFnHYd5g591LtkCZ1bibDp83aLWQLv2ica48KDGz8qNC2lAMfugtwzp1g/132', 0, 0);
INSERT INTO `user_basic_info` VALUES ('oxrwq04qsAvL_ww7WXDB2YWspLsU', '伐木磊', '中国西安', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqAXcbicN2gQuwJ40wgnZibNmbz1yRoh67pcoYNteGL2YkjyYJc4GRVHcF2YLnMWNduMTvrIKJNNGicg/132', 0, 1);
INSERT INTO `user_basic_info` VALUES ('oxrwq05AIltU0GjBppY198QS_au4', '久睡成瘾', '中国氹仔', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI6emm6Ll2iatooe0vL2OP1lJNWByEUE0Y1s1z3nibobog6RtrYlSp1umaS7tUxicarKkk2uPoM7BcBw/132', 0, 1);
INSERT INTO `user_basic_info` VALUES ('oxrwq05hQTJ1MTlhTZ8bPXFN73yQ', '南～', '', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/ooYDTtTQHLicRibCM33sK8WlyCUlRibcp0Z5eL4JHlqsm8LXQsh8koFFSb8qEoYyTultKyX5aG0yic4icAPPZfHIZgA/132', 0, 0);
INSERT INTO `user_basic_info` VALUES ('oxrwq05IREEO8XDlFXnhvNsEcP1k', 'Aries', '中国宝鸡', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/KpMCvItXghU73pnWSgDv2QtxqEWHOw4WvibwVIN1UDVbFoR5uVQn3Gfqrvv7fw150okV1ricw16icfhicNI6KYl4vg/132', 0, 2);
INSERT INTO `user_basic_info` VALUES ('oxrwq06MVoJNKPkAP2lMqzidSyy8', 'IM', '百慕大', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/YaDBP3rHNXvv3Xyrs7vBZZMA8nyhYycxAd2QrHnurwhicebA8dgxML9HjQZSl1Fs0icTRGFrrFnhnSCgvEsLrUaA/132', 0, 2);
INSERT INTO `user_basic_info` VALUES ('oxrwq08uBnQD2I6jNj3ZCrN1OyEg', '啊！杨胖子', '百慕大', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/C4Qhy5XHSe7MjhSkrIw9gUX1Diaj0UKPoHMGyvfc56L6kiacTXlYMuK9o98uERK1vhrRcLEkLZPDUmJ1XWuOibQ5A/132', 0, 2);
INSERT INTO `user_basic_info` VALUES ('oxrwq09vdgSlpT775HkGq1yc_2m0', '另维', '不丹', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/MSMLyPK5WgkX6PZ1R0k5LulbGZLuvZBw1YXIFAhohRq29eJg1pNkOB25eLJoOdJUff98Fz9HMslvadXsDLD3sg/132', 0, 2);
INSERT INTO `user_basic_info` VALUES ('oxrwq0wSwjwYbbdx6Dtjtc2Ym0ME', '栽西红柿的人', '', '13409544219', 'http://thirdwx.qlogo.cn/mmopen/vi_32/GkMk4gBlfZdFonXFQaW87qraV1XMD6ibDHs10mX7AsJV1oYALogbib4wOfdTIbbR7yjMibj1ORVNN0fl1Rb6huohg/132', 0, 2);
INSERT INTO `user_basic_info` VALUES ('oxrwq0wVnozvgOhy_72QUnnuRfng', '.', '圣诞岛', '18292831771', 'http://thirdwx.qlogo.cn/mmopen/vi_32/j2wow3nLf8NZ0ibXSl6nE7QeyuwJXSrAWxW2ovp8FqOKmwSc5AD3p6OqEcQwVqwmvawF44ja1pc1vOEm10hdNNg/132', 0, 2);
INSERT INTO `user_basic_info` VALUES ('oxrwq0xnvviKpELIQqA_v4HCtem4', ' 晨曦 ', '', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/3peVc9cwjia5M9VUJa8tCO6Ubq2Vawd8jcRgdqZCkBianVvKwFPBBrmyMY8aQNTlcLRib6MJiaMYP8gicbDhd9GtUVg/132', 0, 0);
INSERT INTO `user_basic_info` VALUES ('oxrwq0xrKKyqiAGE8O9TM3L1yaQY', 'ahojcn', '中国', '15229720759', 'http://thirdwx.qlogo.cn/mmopen/vi_32/hyCfOptGJaWH4dYwqJNlCSBnPmJqJHBJ32FtxIjia3yQonGLHjQu1BYq9EBQ2BjM5u1VLfLN6fX3CXGcqG5CP2g/132', 0, 1);
INSERT INTO `user_basic_info` VALUES ('oxrwq0zaS8lC1PgJReAlUC6lbrWU', 'sunshine', '法国', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/DpXRpkSsmWxfibUmI66TKT4T9GUe5cHWn3NVtGkkenic0Z9smvnia0KB7nduF2cqtWyjXWsmZEqkNrzIlL5t1CvtQ/132', 0, 1);
INSERT INTO `user_basic_info` VALUES ('oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk', 'Tim', '泽西岛', '15291418231', 'http://thirdwx.qlogo.cn/mmopen/vi_32/bxVEQxwmOLibgHtYurJxvW0yicXLVcTCUiaDQDqibEyoIKwS7ZRdOsZL02RibF79vdNt6GgFKMr4fuDNV8T7X3ficTfg/132', 0, 1);
INSERT INTO `user_basic_info` VALUES ('oxrwq0_C0HPDloTmvpncY7TucQQg', '٩๑乛㉨乛๑', '泽西岛', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/xUvibH1XlwuJN70PjKKMSoes5IvvYjVicSPXe0XmXp9RIwEWkwljnTia1CiaT0ESq5Uj4BK60WXVn8EGV40ybS20Aw/132', 0, 2);
INSERT INTO `user_basic_info` VALUES ('oxrwq0_zXhDizuK9BLxcwbCYgGZc', '......', '柬埔寨', NULL, 'http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83erF1G2howbLZ7CnX91QMaBhTNZgsztw65ALp2Ax52Qiab4V3CQPTYMsTflF2zCqXrYG19THRqmEM8g/132', 0, 2);

-- ----------------------------
-- Table structure for user_grade
-- ----------------------------
DROP TABLE IF EXISTS `user_grade`;
CREATE TABLE `user_grade`  (
  `open_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `student_grade` int(11) DEFAULT 0,
  `company_grade` int(11) DEFAULT 0,
  `club_grade` int(11) DEFAULT 0,
  PRIMARY KEY (`open_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
