/*
 Navicat Premium Data Transfer

 Source Server         : AirCyan
 Source Server Type    : MySQL
 Source Server Version : 100137
 Source Host           : localhost:3306
 Source Schema         : maintancedb

 Target Server Type    : MySQL
 Target Server Version : 100137
 File Encoding         : 65001

 Date: 23/09/2024 16:36:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for approval_info
-- ----------------------------
DROP TABLE IF EXISTS `approval_info`;
CREATE TABLE `approval_info`  (
  `approval_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `plan_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `father_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `applicant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `manip_time` datetime(0) NULL DEFAULT NULL,
  `approval_status` tinyint(255) NULL DEFAULT NULL,
  `step_order` tinyint(255) NULL DEFAULT NULL,
  `approval_remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`approval_id`) USING BTREE,
  INDEX `pid_fk`(`plan_id`) USING BTREE,
  INDEX `aid_fk`(`father_id`) USING BTREE,
  CONSTRAINT `aid_fk` FOREIGN KEY (`father_id`) REFERENCES `approval_info` (`approval_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `pid_fk` FOREIGN KEY (`plan_id`) REFERENCES `maintance_info` (`plan_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of approval_info
-- ----------------------------

-- ----------------------------
-- Table structure for check_info
-- ----------------------------
DROP TABLE IF EXISTS `check_info`;
CREATE TABLE `check_info`  (
  `check_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `equip_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `check_time` datetime(0) NULL DEFAULT NULL,
  `worker_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `check_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `check_out` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`check_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of check_info
-- ----------------------------

-- ----------------------------
-- Table structure for checkitem_info
-- ----------------------------
DROP TABLE IF EXISTS `checkitem_info`;
CREATE TABLE `checkitem_info`  (
  `checkitem_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `checkitem_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `check_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `checkitem_time` datetime(0) NULL DEFAULT NULL,
  `checkitem_out` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`checkitem_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of checkitem_info
-- ----------------------------

-- ----------------------------
-- Table structure for equip_info
-- ----------------------------
DROP TABLE IF EXISTS `equip_info`;
CREATE TABLE `equip_info`  (
  `equip_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `equip_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equip_pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equip_status` tinyint(4) NULL DEFAULT NULL,
  `valid_time` tinyint(4) NULL DEFAULT NULL,
  `purchase_date` datetime(0) NULL DEFAULT NULL,
  `fix_big` tinyint(4) NULL DEFAULT NULL,
  `fix_medium` tinyint(4) NULL DEFAULT NULL,
  `fix_small` tinyint(4) NULL DEFAULT NULL,
  `count_big` tinyint(255) NULL DEFAULT NULL,
  `count_medium` tinyint(255) NULL DEFAULT NULL,
  `count_small` tinyint(255) NULL DEFAULT NULL,
  `last_maintance` datetime(0) NULL DEFAULT NULL,
  `remarks` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`equip_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of equip_info
-- ----------------------------

-- ----------------------------
-- Table structure for maintance_info
-- ----------------------------
DROP TABLE IF EXISTS `maintance_info`;
CREATE TABLE `maintance_info`  (
  `plan_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `equip_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `plan_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `maintance_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `maintance_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`plan_id`) USING BTREE,
  INDEX `eid_fk`(`equip_id`) USING BTREE,
  CONSTRAINT `eid_fk` FOREIGN KEY (`equip_id`) REFERENCES `equip_info` (`equip_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of maintance_info
-- ----------------------------

-- ----------------------------
-- Table structure for menu_info
-- ----------------------------
DROP TABLE IF EXISTS `menu_info`;
CREATE TABLE `menu_info`  (
  `menu_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_order` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of menu_info
-- ----------------------------

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `order_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `equip_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `plan_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `deliver_time` datetime(0) NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `worker_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `order_record` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `pid_fk_2`(`plan_id`) USING BTREE,
  INDEX `eid_fk_2`(`equip_id`) USING BTREE,
  INDEX `wid_fk_1`(`worker_id`) USING BTREE,
  CONSTRAINT `eid_fk_2` FOREIGN KEY (`equip_id`) REFERENCES `equip_info` (`equip_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `pid_fk_2` FOREIGN KEY (`plan_id`) REFERENCES `maintance_info` (`plan_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `wid_fk_1` FOREIGN KEY (`worker_id`) REFERENCES `worker_info` (`worker_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order_info
-- ----------------------------

-- ----------------------------
-- Table structure for role_info
-- ----------------------------
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE `role_info`  (
  `role_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_info
-- ----------------------------

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `rm_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`rm_id`) USING BTREE,
  INDEX `rid_fk_1`(`role_id`) USING BTREE,
  INDEX `mid_fk_1`(`menu_id`) USING BTREE,
  CONSTRAINT `mid_fk_1` FOREIGN KEY (`menu_id`) REFERENCES `menu_info` (`menu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `rid_fk_1` FOREIGN KEY (`role_id`) REFERENCES `role_info` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `user_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `userpwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `ur_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ur_id`) USING BTREE,
  INDEX `uid_fk_1`(`user_id`) USING BTREE,
  INDEX `rid_fk_2`(`role_id`) USING BTREE,
  CONSTRAINT `rid_fk_2` FOREIGN KEY (`role_id`) REFERENCES `role_info` (`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `uid_fk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_role
-- ----------------------------

-- ----------------------------
-- Table structure for worker_info
-- ----------------------------
DROP TABLE IF EXISTS `worker_info`;
CREATE TABLE `worker_info`  (
  `worker_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `worker_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dept` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_hour` decimal(10, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`worker_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of worker_info
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
