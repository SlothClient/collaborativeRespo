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

 Date: 13/10/2024 22:10:57
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
  `father_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `applicant_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `manip_time` datetime(0) NULL DEFAULT NULL,
  `approval_status` tinyint(4) NULL DEFAULT NULL,
  `step_order` tinyint(4) NULL DEFAULT NULL,
  `approval_remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `is_deleted` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`approval_id`) USING BTREE,
  INDEX `pid_fk`(`plan_id`) USING BTREE,
  INDEX `aid_fk`(`father_id`) USING BTREE,
  INDEX `applicantId_fk`(`applicant_id`) USING BTREE,
  CONSTRAINT `aid_fk` FOREIGN KEY (`father_id`) REFERENCES `approval_info` (`approval_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `applicantId_fk` FOREIGN KEY (`applicant_id`) REFERENCES `user_info` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `pid_fk` FOREIGN KEY (`plan_id`) REFERENCES `maintance_info` (`plan_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of approval_info
-- ----------------------------
INSERT INTO `approval_info` VALUES ('1839913621563109378', '1839913621521166337', NULL, 'U001', '2024-09-28 14:22:45', 0, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839913621563109379', '1839913621521166337', NULL, NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839913621609246721', '1839913621521166337', NULL, NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839913803625263106', '1839913803625263105', NULL, 'U001', '2024-09-28 14:23:29', 0, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839913803667206146', '1839913803625263105', NULL, NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839913803667206147', '1839913803625263105', NULL, NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839920883249672195', '1839920883249672194', NULL, 'U001', '2024-09-28 14:51:37', 0, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839920883249672196', '1839920883249672194', NULL, NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839920883325169666', '1839920883249672194', NULL, NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839921085444485122', '1839921085398347778', NULL, 'U001', '2024-09-28 14:52:25', 0, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839921085444485123', '1839921085398347778', NULL, NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839921085444485124', '1839921085398347778', NULL, NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839922184649920514', '1839922184649920513', NULL, 'U001', '2024-09-28 14:56:47', 0, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839922184649920515', '1839922184649920513', NULL, NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1839922184649920516', '1839922184649920513', NULL, NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1840350924668354561', '1840350924613828610', NULL, 'U003', '2024-09-29 19:20:27', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1840350924668354562', '1840350924613828610', NULL, 'U002', '2024-09-29 21:08:03', 2, 1, '我是manager,我同意', 0);
INSERT INTO `approval_info` VALUES ('1840350924668354563', '1840350924613828610', NULL, 'U001', '2024-09-29 21:08:44', 2, 2, '我是admin,我同意', 0);
INSERT INTO `approval_info` VALUES ('1840382441503215617', '1840382441486438402', NULL, 'U002', '2024-09-29 21:25:41', 0, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1840382441553547265', '1840382441486438402', NULL, NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1840382441553547266', '1840382441486438402', NULL, NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1840389569781657601', '1840389569731325954', NULL, 'U003', '2024-09-29 21:54:00', 0, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1840389569781657602', '1840389569731325954', NULL, NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1840389569781657603', '1840389569731325954', NULL, NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1840580697608937475', '1840580697608937474', NULL, 'U003', '2024-09-30 10:33:29', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1840580697680240641', '1840580697608937474', NULL, 'U002', '2024-09-30 10:34:09', 2, 1, '我是manager，我同意该计划', 0);
INSERT INTO `approval_info` VALUES ('1840580697680240642', '1840580697608937474', NULL, 'U001', '2024-09-30 10:34:45', 2, 2, '我是admin，我同意该计划', 0);
INSERT INTO `approval_info` VALUES ('1840585257077391361', '1840585257048031233', NULL, 'U003', '2024-09-30 10:51:36', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1840585257077391362', '1840585257048031233', NULL, 'U002', '2024-09-30 10:56:10', 2, 1, '看你不爽，驳回你', 0);
INSERT INTO `approval_info` VALUES ('1840585257077391363', '1840585257048031233', NULL, 'U001', '2024-09-30 10:57:16', 2, 2, '啊？', 0);
INSERT INTO `approval_info` VALUES ('1840587262277664770', '1840587262277664769', NULL, 'U003', '2024-09-30 10:59:34', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1840587262340579330', '1840587262277664769', NULL, 'U002', '2024-09-30 11:00:10', 3, 1, '驳回', 0);
INSERT INTO `approval_info` VALUES ('1840587262340579331', '1840587262277664769', NULL, 'U001', '2024-09-30 11:04:14', 2, 2, '测试一下驳回了能不能再次通过', 0);
INSERT INTO `approval_info` VALUES ('1840754307132727298', '1840754307044646914', NULL, 'U003', '2024-09-30 22:03:20', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1840754307174670337', '1840754307044646914', NULL, 'U002', '2024-09-30 22:05:04', 3, 1, 'manager驳回', 0);
INSERT INTO `approval_info` VALUES ('1840754307220807681', '1840754307044646914', NULL, 'U001', '2024-09-30 22:05:49', 2, 2, 'manager驳回了admin还能同意？', 0);
INSERT INTO `approval_info` VALUES ('1841110877431635970', '1841110877385498626', NULL, 'U003', '2024-10-01 21:40:13', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841110877490356226', '1841110877385498626', NULL, NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841110877490356227', '1841110877385498626', NULL, NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841111036626444290', '1841111036592889857', NULL, 'U003', '2024-10-01 21:40:51', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841111036626444291', '1841111036592889857', NULL, NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841111036651610114', '1841111036592889857', NULL, NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841111151470682113', '1841111151407767553', NULL, 'U003', '2024-10-01 21:41:19', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841111151470682114', '1841111151407767553', NULL, 'U002', '2024-10-01 22:04:50', 3, 1, '我就要驳回', 0);
INSERT INTO `approval_info` VALUES ('1841111151500042241', '1841111151407767553', NULL, NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841113178846187522', '1841113178846187521', NULL, 'U003', '2024-10-01 21:49:22', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841113178917490690', '1841113178846187521', NULL, 'U002', '2024-10-01 21:54:26', 2, 1, '我同意该计划', 0);
INSERT INTO `approval_info` VALUES ('1841113178917490691', '1841113178846187521', NULL, 'U001', '2024-10-01 21:55:12', 3, 2, '我不同意', 0);
INSERT INTO `approval_info` VALUES ('1841391043391250433', '1841391043374473217', NULL, 'U003', '2024-10-02 16:13:30', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841391043391250434', '1841391043374473217', NULL, 'U002', '2024-10-02 16:23:41', 2, 1, '下一级同意', 0);
INSERT INTO `approval_info` VALUES ('1841391043391250435', '1841391043374473217', NULL, 'U001', '2024-10-02 16:25:57', 3, 2, '拒绝不需要理由', 0);
INSERT INTO `approval_info` VALUES ('1841395469682204675', '1841395469682204674', NULL, 'U003', '2024-10-02 16:31:06', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841395469745119233', '1841395469682204674', NULL, 'U002', '2024-10-02 16:32:40', 3, 1, '驳回试试', 0);
INSERT INTO `approval_info` VALUES ('1841395469745119234', '1841395469682204674', NULL, NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841397446306648066', '1841397446294065153', NULL, 'U003', '2024-10-02 16:38:57', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841397446352785410', '1841397446294065153', NULL, 'U002', '2024-10-02 16:39:31', 3, 1, '我不同意', 0);
INSERT INTO `approval_info` VALUES ('1841397446352785411', '1841397446294065153', NULL, NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841397751782002690', '1841397751719088130', NULL, 'U003', '2024-10-02 16:40:10', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841397751807168513', '1841397751719088130', NULL, 'U002', '2024-10-02 16:40:33', 2, 1, '我同意', 0);
INSERT INTO `approval_info` VALUES ('1841397751807168514', '1841397751719088130', NULL, 'U001', '2024-10-02 16:40:45', 3, 2, '我不同意', 0);
INSERT INTO `approval_info` VALUES ('1841408674227769346', '1841408674164854786', NULL, 'U003', '2024-10-02 17:23:34', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1841408674227769347', '1841408674164854786', NULL, 'U002', '2024-10-02 17:23:57', 2, 1, '通过', 0);
INSERT INTO `approval_info` VALUES ('1841408674257129474', '1841408674164854786', NULL, NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1842839948779827203', '1842839948779827202', NULL, 'U003', '2024-10-06 16:10:56', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1842839948846936065', '1842839948779827202', NULL, 'U002', '2024-10-06 16:11:55', 2, 1, '先同意试试看', 0);
INSERT INTO `approval_info` VALUES ('1842839948846936066', '1842839948779827202', NULL, 'U001', '2024-10-06 16:12:41', 3, 2, '额不同意', 0);
INSERT INTO `approval_info` VALUES ('1843287985016786946', '1843287985004204034', NULL, 'U003', '2024-10-07 21:51:16', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1843287985016786947', '1843287985004204034', NULL, 'U002', '2024-10-07 21:51:58', 2, 1, '同意测试派单', 0);
INSERT INTO `approval_info` VALUES ('1843287985016786948', '1843287985004204034', NULL, 'U001', '2024-10-07 21:52:35', 2, 2, '我同意测试派单', 0);
INSERT INTO `approval_info` VALUES ('1843655390027403266', '1843655390027403265', NULL, 'U003', '2024-10-08 22:11:13', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1843655390027403267', '1843655390027403265', '1843655390027403266', 'U002', '2024-10-08 22:23:29', 2, 1, 'pass', 0);
INSERT INTO `approval_info` VALUES ('1843655390027403268', '1843655390027403265', '1843655390027403267', 'U001', '2024-10-08 22:23:49', 2, 2, 'pass', 0);
INSERT INTO `approval_info` VALUES ('1844045153909604354', '1844045153909604353', NULL, 'U003', '2024-10-10 00:00:00', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844045153951547394', '1844045153909604353', '1844045153909604354', 'U002', '2024-10-12 22:49:15', 2, 1, '同意了', 0);
INSERT INTO `approval_info` VALUES ('1844045153951547395', '1844045153909604353', '1844045153951547394', 'U001', '2024-10-12 22:49:55', 2, 2, '我也同意了', 0);
INSERT INTO `approval_info` VALUES ('1844045153951547397', '1844045153951547396', NULL, 'U003', '2024-10-10 00:00:00', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844045153951547398', '1844045153951547396', '1844045153951547397', NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844045154014461953', '1844045153951547396', '1844045153951547398', NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844045154014461955', '1844045154014461954', NULL, 'U003', '2024-10-10 00:00:00', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844045154014461956', '1844045154014461954', '1844045154014461955', NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844045154014461957', '1844045154014461954', '1844045154014461956', NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844045154077376513', '1844045154014461958', NULL, 'U003', '2024-10-10 00:00:00', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844045154077376514', '1844045154014461958', '1844045154077376513', NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844045154077376515', '1844045154014461958', '1844045154077376514', NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844045154077376517', '1844045154077376516', NULL, 'U003', '2024-10-10 00:00:00', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844045154077376518', '1844045154077376516', '1844045154077376517', NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844045154077376519', '1844045154077376516', '1844045154077376518', NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844407541771067395', '1844407541771067394', NULL, 'U003', '2024-10-11 00:00:00', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844407541771067396', '1844407541771067394', '1844407541771067395', NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844407541838176257', '1844407541771067394', '1844407541771067396', NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929611563011', '1844769929611563010', NULL, 'U003', '2024-10-12 00:00:00', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929611563012', '1844769929611563010', '1844769929611563011', NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929611563013', '1844769929611563010', '1844769929611563012', NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929674477570', '1844769929674477569', NULL, 'U003', '2024-10-12 00:00:00', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929674477571', '1844769929674477569', '1844769929674477570', NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929674477572', '1844769929674477569', '1844769929674477571', NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929674477574', '1844769929674477573', NULL, 'U003', '2024-10-12 00:00:00', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929674477575', '1844769929674477573', '1844769929674477574', NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929674477576', '1844769929674477573', '1844769929674477575', NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929674477578', '1844769929674477577', NULL, 'U003', '2024-10-12 00:00:00', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929674477579', '1844769929674477577', '1844769929674477578', NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929674477580', '1844769929674477577', '1844769929674477579', NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929741586433', '1844769929674477581', NULL, 'U003', '2024-10-12 00:00:00', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929741586434', '1844769929674477581', '1844769929741586433', NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1844769929741586435', '1844769929674477581', '1844769929741586434', NULL, NULL, 0, 2, NULL, 0);
INSERT INTO `approval_info` VALUES ('1845132317745479681', '1845132317703536642', NULL, 'U003', '2024-10-13 00:00:00', 2, 0, NULL, 0);
INSERT INTO `approval_info` VALUES ('1845132317888086018', '1845132317703536642', '1845132317745479681', NULL, NULL, 0, 1, NULL, 0);
INSERT INTO `approval_info` VALUES ('1845132317955194881', '1845132317703536642', '1845132317888086018', NULL, NULL, 0, 2, NULL, 0);

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
INSERT INTO `check_info` VALUES ('CH001', 'EQ001', '2024-10-01 10:00:00', 'W001', '钻床月度常规检查', NULL);
INSERT INTO `check_info` VALUES ('CH002', 'EQ002', '2024-11-15 09:00:00', 'W002', '车床换油检查', NULL);
INSERT INTO `check_info` VALUES ('CH003', 'EQ003', '2025-01-10 10:00:00', 'W003', '铣床年度大修初检', NULL);
INSERT INTO `check_info` VALUES ('CH004', 'EQ004', '2024-12-05 10:00:00', 'W004', '磨床半年度调试检查', NULL);
INSERT INTO `check_info` VALUES ('CH005', 'EQ005', '2024-10-20 09:00:00', 'W005', '冲压机故障修复检查', NULL);
INSERT INTO `check_info` VALUES ('CH006', 'EQ006', '2024-11-01 14:00:00', 'W006', '焊接机季度例行检查', NULL);
INSERT INTO `check_info` VALUES ('CH007', 'EQ007', '2025-02-01 09:00:00', 'W007', '注塑机大修前检查', NULL);
INSERT INTO `check_info` VALUES ('CH008', 'EQ008', '2024-10-10 10:00:00', 'W008', '切割机月度维护检查', NULL);
INSERT INTO `check_info` VALUES ('CH009', 'EQ009', '2025-01-05 09:00:00', 'W009', '压力机年度校准检查', NULL);
INSERT INTO `check_info` VALUES ('CH010', 'EQ010', '2024-11-20 10:00:00', 'W010', '包装机季度保养检查', NULL);

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
INSERT INTO `checkitem_info` VALUES ('CI001', '油位', 'CH001', '2024-10-01 10:15:00', '正常');
INSERT INTO `checkitem_info` VALUES ('CI002', '皮带张力', 'CH001', '2024-10-01 10:30:00', '已调整');
INSERT INTO `checkitem_info` VALUES ('CI003', '油质', 'CH002', '2024-11-15 09:15:00', '已更换');
INSERT INTO `checkitem_info` VALUES ('CI004', '轴承状态', 'CH003', '2025-01-10 10:30:00', '良好');
INSERT INTO `checkitem_info` VALUES ('CI005', '砂轮平衡', 'CH004', '2024-12-05 10:45:00', '已校正');
INSERT INTO `checkitem_info` VALUES ('CI006', '液压系统', 'CH005', '2024-10-20 09:30:00', '已修复');
INSERT INTO `checkitem_info` VALUES ('CI007', '电极状态', 'CH006', '2024-11-01 14:15:00', '需更换');
INSERT INTO `checkitem_info` VALUES ('CI008', '螺杆清洁度', 'CH007', '2025-02-01 09:45:00', '已清洁');
INSERT INTO `checkitem_info` VALUES ('CI009', '刀片锋利度', 'CH008', '2024-10-10 10:20:00', '良好');
INSERT INTO `checkitem_info` VALUES ('CI010', '压力表准确性', 'CH009', '2025-01-05 09:30:00', '已校准');

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
  `count_big` tinyint(4) NULL DEFAULT NULL,
  `count_medium` tinyint(4) NULL DEFAULT NULL,
  `count_small` tinyint(4) NULL DEFAULT NULL,
  `last_maintance` datetime(0) NULL DEFAULT NULL,
  `remarks` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`equip_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of equip_info
-- ----------------------------
INSERT INTO `equip_info` VALUES ('EQ001', '钻床', NULL, 1, 60, '2023-01-15 00:00:00', 12, 6, 3, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `equip_info` VALUES ('EQ002', '车床', NULL, 1, 48, '2023-03-20 00:00:00', 18, 9, 4, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `equip_info` VALUES ('EQ003', '铣床', NULL, 1, 72, '2022-11-10 00:00:00', 24, 12, 6, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `equip_info` VALUES ('EQ004', '磨床', NULL, 1, 36, '2023-05-05 00:00:00', 15, 8, 4, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `equip_info` VALUES ('EQ005', '冲压机', NULL, 0, 84, '2022-09-01 00:00:00', 30, 15, 7, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `equip_info` VALUES ('EQ006', '焊接机', NULL, 1, 24, '2023-07-10 00:00:00', 10, 5, 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `equip_info` VALUES ('EQ007', '注塑机', NULL, 1, 96, '2022-06-15 00:00:00', 36, 18, 9, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `equip_info` VALUES ('EQ008', '切割机', NULL, 1, 30, '2023-08-20 00:00:00', 12, 6, 3, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `equip_info` VALUES ('EQ009', '压力机', NULL, 1, 54, '2022-12-05 00:00:00', 20, 10, 5, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `equip_info` VALUES ('EQ010', '包装机', NULL, 1, 42, '2023-02-28 00:00:00', 16, 8, 4, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for maintance_info
-- ----------------------------
DROP TABLE IF EXISTS `maintance_info`;
CREATE TABLE `maintance_info`  (
  `plan_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `equip_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `plan_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT NULL,
  `maintance_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `maintance_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_deleted` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`plan_id`) USING BTREE,
  INDEX `eid_fk`(`equip_id`) USING BTREE,
  CONSTRAINT `eid_fk` FOREIGN KEY (`equip_id`) REFERENCES `equip_info` (`equip_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of maintance_info
-- ----------------------------
INSERT INTO `maintance_info` VALUES ('1839912235471466497', 'EQ005', '这是一个测试', '2024-09-30 15:00:00', '2024-10-14 16:00:00', 6, '这是一个测试', '小修周期', 0);
INSERT INTO `maintance_info` VALUES ('1839913385746755586', 'EQ005', '这还是一个测试', '2024-09-29 00:02:02', '2024-10-08 00:05:02', 0, '哈哈哈哈', '预防性维护', 0);
INSERT INTO `maintance_info` VALUES ('1839913621521166337', 'EQ004', '这依旧是一个测试', '2024-09-28 00:00:00', '2024-10-02 00:00:00', 0, '国庆快乐', '常规维修', 0);
INSERT INTO `maintance_info` VALUES ('1839913803625263105', 'EQ007', 'this is a test', '2024-10-01 14:22:55', '2024-10-08 14:22:55', 0, 'this is a test\n', '年度检查', 0);
INSERT INTO `maintance_info` VALUES ('1839920883249672194', 'EQ002', '这是一个车床测试', '2024-09-28 08:01:00', '2024-10-29 08:02:00', 0, '啥也不是', '小修周期', 0);
INSERT INTO `maintance_info` VALUES ('1839921085398347778', 'EQ006', '不断地测试', '2024-09-30 00:00:00', '2024-10-01 00:00:00', 0, '测试测试还是测试', '季节性保养', 0);
INSERT INTO `maintance_info` VALUES ('1839922184649920513', 'EQ006', '测试时间', '2024-08-31 00:00:00', '2024-10-29 00:00:00', 0, '测试时间', '紧急维修', 0);
INSERT INTO `maintance_info` VALUES ('1840350924613828610', 'EQ001', '审批求通过', '2024-09-20 19:19:54', '2024-10-17 19:19:54', 6, '呜呜呜', '常规维修', 0);
INSERT INTO `maintance_info` VALUES ('1840382441486438402', 'EQ002', '测试审批', '2024-09-21 21:24:58', '2024-09-30 21:24:58', 0, '测试审批', '紧急维修', 0);
INSERT INTO `maintance_info` VALUES ('1840389569731325954', 'EQ001', '看看看看', '2024-09-21 21:53:41', '2024-10-26 21:53:41', 0, '酷酷酷酷酷酷', '常规维修', 0);
INSERT INTO `maintance_info` VALUES ('1840580697608937474', 'EQ001', '我是user该计划由我发起', '2024-10-09 10:32:45', '2024-10-31 10:32:45', 6, '我是user，计划由我发起', '常规维修', 0);
INSERT INTO `maintance_info` VALUES ('1840585257048031233', 'EQ002', '这是一个驳回测试计划', '2024-10-17 10:51:04', '2024-10-26 10:51:04', 6, '测试驳回', '常规维修', 0);
INSERT INTO `maintance_info` VALUES ('1840587262277664769', 'EQ003', '测试一路驳回', '2024-10-10 10:58:52', '2024-10-24 10:58:52', 6, '我是user，这个测试测试驳回', '小修周期', 0);
INSERT INTO `maintance_info` VALUES ('1840754307044646914', 'EQ009', 'user发起', '2024-10-15 22:02:27', '2024-10-26 22:02:27', 6, 'user发起然后manager驳回看admin', '预防性维护', 0);
INSERT INTO `maintance_info` VALUES ('1841110877385498626', 'EQ001', '审批测试1', '2024-10-04 21:39:35', '2024-10-18 00:00:00', 0, '谁国庆在家敲代码啊！', '常规维修', 0);
INSERT INTO `maintance_info` VALUES ('1841111036592889857', 'EQ003', '审批测试2', '2024-10-25 21:40:13', '2024-10-31 00:00:00', 0, '国庆结束你', '预防性维护', 0);
INSERT INTO `maintance_info` VALUES ('1841111151407767553', 'EQ005', '测试计划三', '2024-10-25 21:40:51', '2024-11-21 21:40:51', 3, '结束结束', '季节性保养', 0);
INSERT INTO `maintance_info` VALUES ('1841113178846187521', 'EQ004', '测试图标显示', '2024-10-25 21:48:55', '2024-11-20 21:48:55', 3, '哈哈哈', '常规维修', 0);
INSERT INTO `maintance_info` VALUES ('1841391043374473217', 'EQ004', '国庆敲代码', '2024-11-12 16:12:48', '2024-11-14 16:12:48', 3, '能不能行', '紧急维修', 0);
INSERT INTO `maintance_info` VALUES ('1841395469682204674', 'EQ001', '一个要用来驳回的计划', '2024-10-26 16:30:40', '2024-11-20 16:30:40', 3, '不行', '零部件更换', 0);
INSERT INTO `maintance_info` VALUES ('1841397446294065153', 'EQ002', '哈哈哈', '2024-10-26 16:38:41', '2024-11-21 16:38:41', 3, '哈哈哈', '小修周期', 0);
INSERT INTO `maintance_info` VALUES ('1841397751719088130', 'EQ003', '测试', '2024-10-26 16:39:51', '2024-11-21 16:39:51', 3, '呜呜呜', '年度检查', 0);
INSERT INTO `maintance_info` VALUES ('1841408674164854786', 'EQ003', '33333333', '2024-10-19 00:00:00', '2024-11-12 00:00:00', 1, '666666\n', '常规维修', 0);
INSERT INTO `maintance_info` VALUES ('1842839948779827202', 'EQ001', '新增计划', '2024-10-18 16:10:26', '2024-11-14 16:10:26', 3, '查看详情', '小修周期', 0);
INSERT INTO `maintance_info` VALUES ('1843287985004204034', 'EQ002', '测试派单', '2024-10-26 21:50:53', '2024-11-21 00:00:00', 6, '测试派单', '常规维修', 0);
INSERT INTO `maintance_info` VALUES ('1843655390027403265', 'EQ005', '压力测试', '2024-10-09 00:00:00', '2024-11-15 00:00:00', 6, 'testOnly', '预防性维护', 0);
INSERT INTO `maintance_info` VALUES ('1844045153909604353', 'EQ004', '定期维护-小修周期', '2024-10-10 00:00:00', '2024-10-17 00:00:00', 2, '保养计划磨床小修周期', '小修周期', 0);
INSERT INTO `maintance_info` VALUES ('1844045153951547396', 'EQ005', '定期维护-小修周期', '2024-10-10 00:00:00', '2024-10-17 00:00:00', 0, '保养计划冲压机小修周期', '小修周期', 0);
INSERT INTO `maintance_info` VALUES ('1844045154014461954', 'EQ006', '定期维护-小修周期', '2024-10-10 00:00:00', '2024-10-17 00:00:00', 0, '保养计划焊接机小修周期', '小修周期', 0);
INSERT INTO `maintance_info` VALUES ('1844045154014461958', 'EQ008', '定期维护-小修周期', '2024-10-10 00:00:00', '2024-10-17 00:00:00', 0, '保养计划切割机小修周期', '小修周期', 0);
INSERT INTO `maintance_info` VALUES ('1844045154077376516', 'EQ009', '定期维护-小修周期', '2024-10-10 00:00:00', '2024-10-17 00:00:00', 0, '保养计划压力机小修周期', '小修周期', 0);
INSERT INTO `maintance_info` VALUES ('1844407541771067394', 'EQ004', '定期维护-大修周期', '2024-10-11 00:00:00', '2024-10-18 00:00:00', 0, '保养计划磨床大修周期', '大修周期', 0);
INSERT INTO `maintance_info` VALUES ('1844769929611563010', 'EQ001', '定期维护-大修周期', '2024-10-12 00:00:00', '2024-10-19 00:00:00', 0, '保养计划钻床大修周期', '大修周期', 0);
INSERT INTO `maintance_info` VALUES ('1844769929674477569', 'EQ002', '定期维护-小修周期', '2024-10-12 00:00:00', '2024-10-19 00:00:00', 0, '保养计划车床小修周期', '小修周期', 0);
INSERT INTO `maintance_info` VALUES ('1844769929674477573', 'EQ003', '定期维护-小修周期', '2024-10-12 00:00:00', '2024-10-19 00:00:00', 0, '保养计划铣床小修周期', '小修周期', 0);
INSERT INTO `maintance_info` VALUES ('1844769929674477577', 'EQ006', '定期维护-大修周期', '2024-10-12 00:00:00', '2024-10-19 00:00:00', 0, '保养计划焊接机大修周期', '大修周期', 0);
INSERT INTO `maintance_info` VALUES ('1844769929674477581', 'EQ010', '定期维护-大修周期', '2024-10-12 00:00:00', '2024-10-19 00:00:00', 0, '保养计划包装机大修周期', '大修周期', 0);
INSERT INTO `maintance_info` VALUES ('1845132317703536642', 'EQ008', '定期维护-大修周期', '2024-10-13 00:00:00', '2024-10-20 00:00:00', 0, '保养计划切割机大修周期', '大修周期', 0);

-- ----------------------------
-- Table structure for maintance_type
-- ----------------------------
DROP TABLE IF EXISTS `maintance_type`;
CREATE TABLE `maintance_type`  (
  `type_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `maintance_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of maintance_type
-- ----------------------------
INSERT INTO `maintance_type` VALUES ('MT001', '大修周期');
INSERT INTO `maintance_type` VALUES ('MT002', '小修周期');
INSERT INTO `maintance_type` VALUES ('MT003', '常规维修');
INSERT INTO `maintance_type` VALUES ('MT004', '预防性维护');
INSERT INTO `maintance_type` VALUES ('MT005', '紧急维修');
INSERT INTO `maintance_type` VALUES ('MT006', '季节性保养');
INSERT INTO `maintance_type` VALUES ('MT007', '年度检查');
INSERT INTO `maintance_type` VALUES ('MT008', '中修周期');
INSERT INTO `maintance_type` VALUES ('MT009', '设备校准');
INSERT INTO `maintance_type` VALUES ('MT010', '零部件更换');

-- ----------------------------
-- Table structure for menu_info
-- ----------------------------
DROP TABLE IF EXISTS `menu_info`;
CREATE TABLE `menu_info`  (
  `menu_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_order` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of menu_info
-- ----------------------------
INSERT INTO `menu_info` VALUES ('M001', '系统首页', NULL, 'home', 'HomeFilled', 'home:view_dashboard', '1');
INSERT INTO `menu_info` VALUES ('M002', '设备信息', NULL, 'Device', 'Cpu', 'device:manage_equipment', '2');
INSERT INTO `menu_info` VALUES ('M003', '保养计划', '', 'maintenancePlan/index', 'SetUp', 'maintenance:manage_plan', '3');
INSERT INTO `menu_info` VALUES ('M004', '审批管理', NULL, 'approval/index', 'Check', 'approval:manage', '4');
INSERT INTO `menu_info` VALUES ('M005', '工单管理', NULL, 'order', 'DocumentChecked', 'workorder:manage_order', '5');
INSERT INTO `menu_info` VALUES ('M006', '工单计划', 'M005', 'order/WorkOrder', 'Document', 'workorder:manage_plan', '5');
INSERT INTO `menu_info` VALUES ('M007', '工时管理', 'M005', 'order/dynamicDisplay', 'Clock', 'workhours:manage', '5');
INSERT INTO `menu_info` VALUES ('M008', '系统管理', NULL, 'system', 'Setting', 'system:manage_system', '6');
INSERT INTO `menu_info` VALUES ('M009', '用户管理', 'M008', 'system/index', 'User', 'system:manage_user', '6');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `order_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `equip_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `plan_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
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
INSERT INTO `order_info` VALUES ('1843281858862428161', 'EQ005', '1839912235471466497', '2024-10-07 21:26:56', '2024-10-12 18:05:26', 'W002', 'haha', '无法停止战斗！！！无法停止战斗！！！');
INSERT INTO `order_info` VALUES ('1843282125955706882', 'EQ003', '1840587262277664769', '2024-10-07 21:28:00', '2024-12-02 13:14:52', 'W001', 'lallala', '完善功能：添加图片记录。打开工作记录框拉取工作记录与其图片记录，可以进行修改。');
INSERT INTO `order_info` VALUES ('1843282126102507521', 'EQ009', '1840754307044646914', '2024-10-07 21:28:00', '2024-12-02 13:14:52', 'W001', 'aaaaa', NULL);
INSERT INTO `order_info` VALUES ('1843284296696770561', 'EQ001', '1840350924613828610', '2024-10-07 21:36:37', '2024-12-02 13:14:52', 'W001', '测试', NULL);
INSERT INTO `order_info` VALUES ('1843284296772268034', 'EQ001', '1840580697608937474', '2024-10-07 21:36:37', '2024-12-02 13:14:52', 'W001', '测试', NULL);
INSERT INTO `order_info` VALUES ('1843284296826793986', 'EQ002', '1840585257048031233', '2024-10-07 21:36:37', '2024-12-02 13:14:52', 'W001', '测试', NULL);
INSERT INTO `order_info` VALUES ('1843289172399083521', 'EQ002', '1843287985004204034', '2024-10-07 21:55:59', '2024-12-02 13:14:52', 'W002', '测试派单', NULL);
INSERT INTO `order_info` VALUES ('1843656169203257346', 'EQ005', '1843655390027403265', '2024-10-08 22:14:18', '2024-12-02 13:14:52', 'W008', '吴十干', NULL);
INSERT INTO `order_info` VALUES ('1843658681947869185', 'EQ005', '1843655390027403265', '2024-10-08 22:24:17', '2024-12-02 13:14:52', 'W008', '吴十干', NULL);

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
INSERT INTO `role_info` VALUES ('R001', 'admin', 'Full system access');
INSERT INTO `role_info` VALUES ('R002', 'Manager', 'Maintenance management');
INSERT INTO `role_info` VALUES ('R003', 'User', 'Maintenance execution');

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
INSERT INTO `role_menu` VALUES ('RM001', 'R001', 'M001');
INSERT INTO `role_menu` VALUES ('RM002', 'R001', 'M002');
INSERT INTO `role_menu` VALUES ('RM003', 'R001', 'M003');
INSERT INTO `role_menu` VALUES ('RM004', 'R001', 'M004');
INSERT INTO `role_menu` VALUES ('RM005', 'R001', 'M005');
INSERT INTO `role_menu` VALUES ('RM006', 'R001', 'M006');
INSERT INTO `role_menu` VALUES ('RM007', 'R001', 'M007');
INSERT INTO `role_menu` VALUES ('RM008', 'R001', 'M008');
INSERT INTO `role_menu` VALUES ('RM009', 'R001', 'M009');
INSERT INTO `role_menu` VALUES ('RM010', 'R002', 'M001');
INSERT INTO `role_menu` VALUES ('RM011', 'R002', 'M002');
INSERT INTO `role_menu` VALUES ('RM012', 'R002', 'M003');
INSERT INTO `role_menu` VALUES ('RM013', 'R002', 'M004');
INSERT INTO `role_menu` VALUES ('RM014', 'R002', 'M005');
INSERT INTO `role_menu` VALUES ('RM015', 'R002', 'M006');
INSERT INTO `role_menu` VALUES ('RM016', 'R002', 'M007');
INSERT INTO `role_menu` VALUES ('RM017', 'R003', 'M001');
INSERT INTO `role_menu` VALUES ('RM018', 'R003', 'M002');
INSERT INTO `role_menu` VALUES ('RM019', 'R003', 'M003');
INSERT INTO `role_menu` VALUES ('RM020', 'R003', 'M005');
INSERT INTO `role_menu` VALUES ('RM021', 'R003', 'M006');
INSERT INTO `role_menu` VALUES ('RM022', 'R003', 'M007');

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
INSERT INTO `user_info` VALUES ('U001', 'admin', 'password123');
INSERT INTO `user_info` VALUES ('U002', 'manager', 'password123');
INSERT INTO `user_info` VALUES ('U003', 'user', 'password123');
INSERT INTO `user_info` VALUES ('U004', 'zhangsan', '123456');
INSERT INTO `user_info` VALUES ('U005', 'wangwu', 'password123');
INSERT INTO `user_info` VALUES ('U006', 'lisi', '123456');

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
INSERT INTO `user_role` VALUES ('UR001', 'U001', 'R001');
INSERT INTO `user_role` VALUES ('UR002', 'U002', 'R002');
INSERT INTO `user_role` VALUES ('UR003', 'U003', 'R003');

-- ----------------------------
-- Table structure for worker_info
-- ----------------------------
DROP TABLE IF EXISTS `worker_info`;
CREATE TABLE `worker_info`  (
  `worker_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `worker_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dept` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_hour` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`worker_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of worker_info
-- ----------------------------
INSERT INTO `worker_info` VALUES ('W001', '张三', '维修部', 25.50);
INSERT INTO `worker_info` VALUES ('W002', '李四', '工程部', 236.63);
INSERT INTO `worker_info` VALUES ('W003', '王五', '维修部', 22.75);
INSERT INTO `worker_info` VALUES ('W004', '赵六', '质检部', 28.00);
INSERT INTO `worker_info` VALUES ('W005', '钱七', '工程部', 32.50);
INSERT INTO `worker_info` VALUES ('W006', '孙八', '维修部', 24.00);
INSERT INTO `worker_info` VALUES ('W007', '周九', '质检部', 26.75);
INSERT INTO `worker_info` VALUES ('W008', '吴十', '工程部', 31.25);
INSERT INTO `worker_info` VALUES ('W009', '郑十一', '维修部', 23.50);
INSERT INTO `worker_info` VALUES ('W010', '王十二', '质检部', 27.25);

-- ----------------------------
-- View structure for 审批详情视图
-- ----------------------------
DROP VIEW IF EXISTS `审批详情视图`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `审批详情视图` AS select `user_info`.`username` AS `username`,`approval_info`.`manip_time` AS `manip_time`,`approval_info`.`approval_remark` AS `approval_remark`,`approval_info`.`step_order` AS `step_order`,`equip_info`.`equip_name` AS `equip_name`,`maintance_info`.`maintance_desc` AS `maintance_desc`,`maintance_info`.`start_time` AS `start_time`,`maintance_info`.`end_time` AS `end_time`,`maintance_info`.`status` AS `status`,`approval_info`.`approval_status` AS `approval_status` from (((`approval_info` join `maintance_info` on((`approval_info`.`plan_id` = `maintance_info`.`plan_id`))) left join `user_info` on((`approval_info`.`applicant_id` = `user_info`.`user_id`))) join `equip_info` on((`maintance_info`.`equip_id` = `equip_info`.`equip_id`))) where (`maintance_info`.`plan_id` = '1839912235471466497') order by `approval_info`.`step_order`;

-- ----------------------------
-- Records of worker_info
-- ----------------------------

-- ----------------------------
-- Procedure structure for dynamic_maintenance_plan
-- ----------------------------
DROP PROCEDURE IF EXISTS `dynamic_maintenance_plan`;
delimiter ;;
CREATE PROCEDURE `dynamic_maintenance_plan`()
BEGIN
    -- 标识是否结束循环
    DECLARE DONE INT DEFAULT 0;

    -- 维护周期变量
    DECLARE maintenance_cycle TINYINT;
    DECLARE maintenance_type_name VARCHAR(30);
    
    -- 设备信息变量
    DECLARE equip_id VARCHAR(30);
    DECLARE fix_big TINYINT;
    DECLARE fix_medium TINYINT;
    DECLARE fix_small TINYINT;
    DECLARE purchase_date DATETIME;
    DECLARE equip_name VARCHAR(30);

    -- 定义保养计划ID和审批ID的变量
    DECLARE plan_id VARCHAR(30);
    DECLARE applicant_approval_id VARCHAR(30);
    DECLARE first_approval_id VARCHAR(30);
    DECLARE second_approval_id VARCHAR(30);

    
		
		 -- 定义游标
    DECLARE cur CURSOR FOR 
    SELECT equip_id, fix_big, fix_medium, fix_small, purchase_date, equip_name FROM equip_info;

		DECLARE CONTINUE HANDLER FOR NOT FOUND SET DONE = 1;
		
    -- 打开游标
    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO equip_id, fix_big, fix_medium, fix_small, purchase_date, equip_name;

        IF DONE THEN 
            LEAVE read_loop; 
        END IF;

        -- 初始化维护周期
        SET maintenance_cycle = 0;
				
        IF DATEDIFF(CURRENT_DATE(), purchase_date) % fix_big = 0 THEN -- 大修
            SET maintenance_cycle = fix_big;
            SET maintenance_type_name = '大修周期';
						
        ELSEIF DATEDIFF(CURRENT_DATE(), purchase_date) % fix_medium = 0 THEN -- 中修
            SET maintenance_cycle = fix_medium;
            SET maintenance_type_name = '中修周期';
						
        ELSEIF DATEDIFF(CURRENT_DATE(), purchase_date) % fix_small = 0 THEN -- 小修
            SET maintenance_cycle = fix_small;
            SET maintenance_type_name = '小修周期';
						
        END IF;

        IF maintenance_cycle != 0 THEN
            -- 生成新插入的保养计划ID
            SET plan_id = UUID();

            -- 插入保养计划
            INSERT INTO maintance_info (plan_id, equip_id, maintance_desc, maintance_type, plan_name, start_time, end_time, status)
            VALUES (plan_id, equip_id, CONCAT('定期维护-', maintenance_type_name), maintenance_type_name, CONCAT('保养计划-', equip_name, '-', maintenance_cycle), CURRENT_DATE(), DATE_ADD(CURRENT_DATE(), INTERVAL 7 DAY), 0);

            -- 生成审批ID
            SET applicant_approval_id = UUID();
            SET first_approval_id = UUID();
            SET second_approval_id = UUID();

            -- 插入申请人审批记录 默认为U003，状态设置为已申请（2）
            INSERT INTO approval_info (approval_id, plan_id, applicant_id, approval_status, step_order, manip_time)
            VALUES (applicant_approval_id, plan_id, 'U003', 2, 0, CURRENT_TIMESTAMP());

            -- 插入一级审批记录（待审批状态）
            INSERT INTO approval_info (approval_id, plan_id, approval_status, step_order, father_id)
            VALUES (first_approval_id, plan_id, 0, 1, applicant_approval_id);

            -- 插入二级审批记录（待审批状态）
            INSERT INTO approval_info (approval_id, plan_id, approval_status, step_order, father_id)
            VALUES (second_approval_id, plan_id, 0, 2, first_approval_id);
        END IF;
    END LOOP;

    -- 关闭游标
    CLOSE cur;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
