SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mf_baidu_page
-- ----------------------------
DROP TABLE IF EXISTS `mf_baidu_page`;
CREATE TABLE `mf_baidu_page`  (
  `pageid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bd_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`pageid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mf_baidu_page
-- ----------------------------

-- ----------------------------
-- Table structure for mf_baidu_statistics
-- ----------------------------
DROP TABLE IF EXISTS `mf_baidu_statistics`;
CREATE TABLE `mf_baidu_statistics`  (
  `bd_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bd_time` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bd_website` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网站名',
  `bd_domain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '域名',
  `bd_msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `bd_statistics_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '统计地址',
  `bd_js` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '嵌入js',
  PRIMARY KEY (`bd_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mf_baidu_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for mf_field
-- ----------------------------
DROP TABLE IF EXISTS `mf_field`;
CREATE TABLE `mf_field`  (
  `field_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字段uuid',
  `form_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应表单id',
  `field_time` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  `field_type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段类型123456',
  `field_zz_test` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '正则语句',
  `field_zz_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '正则描述',
  `field_filetype` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件上传类型123',
  `field_timetype` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日期时间类型123',
  `field_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `field_name_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字段名称描述',
  `field_select_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选择框的选项（uuid）',
  PRIMARY KEY (`field_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mf_field
-- ----------------------------

-- ----------------------------
-- Table structure for mf_form
-- ----------------------------
DROP TABLE IF EXISTS `mf_form`;
CREATE TABLE `mf_form`  (
  `form_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'UUID',
  `form_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单名字',
  `form_tablename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单数据库名',
  `form_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单描述',
  `form_time` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`form_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mf_form
-- ----------------------------

-- ----------------------------
-- Table structure for mf_page
-- ----------------------------
DROP TABLE IF EXISTS `mf_page`;
CREATE TABLE `mf_page`  (
  `id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键uuid',
  `ctime` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `message` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注说明',
  `formid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表单id',
  `importscript` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '嵌入js的id',
  `pagetype` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面类型 0查询 1新建',
  `canuse` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态0/1',
  `html_width` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面宽度占比（10-100）',
  `pagelimit` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询页面，分页，一页几条',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mf_page
-- ----------------------------

-- ----------------------------
-- Table structure for mf_page_set_add
-- ----------------------------
DROP TABLE IF EXISTS `mf_page_set_add`;
CREATE TABLE `mf_page_set_add`  (
  `pageid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面id',
  `fieldid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字段id',
  `ispost` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '是否提交0/1',
  `fieldstate` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字段属性 0只读 1可编辑 2必填',
  `defaultvalue` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认值',
  `orderby` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`fieldid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mf_page_set_add
-- ----------------------------

-- ----------------------------
-- Table structure for mf_page_set_list
-- ----------------------------
DROP TABLE IF EXISTS `mf_page_set_list`;
CREATE TABLE `mf_page_set_list`  (
  `pageid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '页面id',
  `fieldid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字段id',
  `can_sort` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '能否排序0/1',
  `can_search` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '能否搜索0/1',
  `orderby` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '顺序',
  PRIMARY KEY (`fieldid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mf_page_set_list
-- ----------------------------

-- ----------------------------
-- Table structure for mf_value
-- ----------------------------
DROP TABLE IF EXISTS `mf_value`;
CREATE TABLE `mf_value`  (
  `fieldid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字段id，可重复',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认值'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mf_value
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
