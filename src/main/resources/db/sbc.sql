/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : sbc

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-10-17 21:04:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for auth_permission
-- ----------------------------
DROP TABLE IF EXISTS `auth_permission`;
CREATE TABLE `auth_permission` (
  `id` varchar(64) NOT NULL COMMENT '权限编号',
  `name` varchar(60) NOT NULL COMMENT '权限名称',
  `type` varchar(60) NOT NULL COMMENT '权限类型',
  `icon` varchar(60) NOT NULL COMMENT '图标名称',
  `component` varchar(255) DEFAULT NULL COMMENT 'vue 模块',
  `url` varchar(255) DEFAULT NULL COMMENT '路径匹配规则',
  `path` varchar(200) DEFAULT NULL COMMENT 'q前端访问路径',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父级菜单编号',
  `order_id` tinyint(4) DEFAULT NULL COMMENT '序号',
  `keep_alive` tinyint(2) DEFAULT NULL,
  `require_auth` tinyint(2) DEFAULT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `user_create` varchar(255) NOT NULL,
  `user_modified` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Records of auth_permission
-- ----------------------------
INSERT INTO `auth_permission` VALUES ('1', '菜单管理', 'menu', 'a', '/auth/Permission', '/auth/permission/*', '/home/auth/permission/view', 'b38ca3c-bd59-45ca-9996-3694b2f8d137', '10', '0', '0', '2018-09-22 00:00:00', '2018-10-15 22:33:09', 'a', 'aaaaaaaaaaa');
INSERT INTO `auth_permission` VALUES ('1212', '角色管理', 'menu', 'aa', '/auth/Role', '/auth/role/*', '/home/auth/role/view', 'b38ca3c-bd59-45ca-9996-3694b2f8d137', '5', '0', '0', '2018-09-30 00:00:00', '2018-10-15 22:33:28', 'aaaaaaaaaaa', 'aaaaaaaaaaa');
INSERT INTO `auth_permission` VALUES ('b38ca3c-bd59-45ca-9996-3694b2f8d137', '权限管理', 'menup', 'aa', '/Home', '/auth/**', '/home/aa/aa', '0', '5', '0', '0', '2018-10-10 10:38:43', '2018-10-15 22:33:19', 'aaaaaaaaaaa', 'aaaaaaaaaaa');
INSERT INTO `auth_permission` VALUES ('ce38ca3c-bd59-45ca-9996-3694b2f8d137', '用户管理', 'menu', 'aa', '/auth/User', '/auth/user/*', '/home/auth/user/view', 'b38ca3c-bd59-45ca-9996-3694b2f8d137', '1', '0', '0', '2018-10-10 10:38:43', '2018-10-15 22:33:34', 'aaaaaaaaaaa', 'aaaaaaaaaaa');

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `role_name` varchar(60) NOT NULL COMMENT '角色名称',
  `role_code` varchar(60) NOT NULL COMMENT '角色代码',
  `level` int(11) NOT NULL,
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `user_create` varchar(255) NOT NULL,
  `user_modified` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Records of auth_role
-- ----------------------------
INSERT INTO `auth_role` VALUES ('75f63868-9716-4d6f-b678-5b5cf89962d0', 'admin', 'admin', '12', '2018-09-30 16:10:28', '2018-09-30 16:12:56', 'aaaaaaaaaaa', 'aaaaaaaaaaa');

-- ----------------------------
-- Table structure for auth_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_permission`;
CREATE TABLE `auth_role_permission` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  `permission_id` varchar(64) NOT NULL COMMENT '权限编号',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色权限关联表';

-- ----------------------------
-- Records of auth_role_permission
-- ----------------------------
INSERT INTO `auth_role_permission` VALUES ('24b96d24-bb97-48fe-9bb2-7d75149e0f92', '75f63868-9716-4d6f-b678-5b5cf89962d0', '1212', '2018-10-11 16:23:09', '2018-10-11 16:23:09');
INSERT INTO `auth_role_permission` VALUES ('97a6b625-8db1-4d4a-8172-383ce4f32707', '75f63868-9716-4d6f-b678-5b5cf89962d0', 'ce38ca3c-bd59-45ca-9996-3694b2f8d137', '2018-10-11 16:23:09', '2018-10-11 16:23:09');
INSERT INTO `auth_role_permission` VALUES ('a26cf25f-ad7a-4583-aa7c-18326617d6b7', '75f63868-9716-4d6f-b678-5b5cf89962d0', '1', '2018-10-11 16:23:09', '2018-10-11 16:23:09');
INSERT INTO `auth_role_permission` VALUES ('e7813e89-b177-4202-84d4-fa806a1dccf1', '75f63868-9716-4d6f-b678-5b5cf89962d0', 'b38ca3c-bd59-45ca-9996-3694b2f8d137', '2018-10-11 16:23:09', '2018-10-11 16:23:09');

-- ----------------------------
-- Table structure for auth_user
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
  `id` varchar(64) NOT NULL COMMENT '用户编号',
  `username` varchar(60) NOT NULL COMMENT '用户名',
  `email` varchar(64) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(60) NOT NULL COMMENT ' 密码',
  `locked` smallint(6) NOT NULL DEFAULT '0' COMMENT '是否锁定',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `user_create` varchar(255) NOT NULL,
  `user_modified` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`) COMMENT '用户名不能重复'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of auth_user
-- ----------------------------
INSERT INTO `auth_user` VALUES ('1', 'aaa', '11@qq.com', 'aa', '$2a$10$UWZLv3R02Ees9JlvGIiQruF72aRfd.3DolI9gen8W3AAqADQ6r0re', '1', '33333333333', '2018-09-23 00:00:00', '2018-10-09 00:00:00', 'ss', 'aaaaaaaaaaa');
INSERT INTO `auth_user` VALUES ('739b44d4-70fb-4758-aabc-e115f0290d55', 'admin', 'aa@qq.com', 'admin', '$2a$10$.pCv1zLmf1hDolWXNp8utuPc1Em6qBT6Ku04ygE78JGlSZDdqkc86', '0', '11111111111', '2018-10-11 00:00:00', '2018-10-11 00:00:00', '1', '1');

-- ----------------------------
-- Table structure for auth_user_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `user_id` varchar(64) NOT NULL COMMENT '用户编号',
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  `gmt_create` datetime NOT NULL COMMENT '创建日期',
  `gmt_modified` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

-- ----------------------------
-- Records of auth_user_role
-- ----------------------------
INSERT INTO `auth_user_role` VALUES ('1', '1', '75f63868-9716-4d6f-b678-5b5cf89962d0', '2018-10-11 16:01:02', '2018-10-11 16:01:04');
INSERT INTO `auth_user_role` VALUES ('ffe8a73c-9d47-401e-b795-efc411ba794a', '739b44d4-70fb-4758-aabc-e115f0290d55', '75f63868-9716-4d6f-b678-5b5cf89962d0', '2018-10-11 16:24:35', '2018-10-11 16:24:35');

-- ----------------------------
-- Table structure for sys_encode
-- ----------------------------
DROP TABLE IF EXISTS `sys_encode`;
CREATE TABLE `sys_encode` (
  `id` int(11) NOT NULL,
  `kind` varchar(255) NOT NULL COMMENT '类型',
  `kind_name` varchar(255) NOT NULL COMMENT '类型名称',
  `encode` varchar(255) NOT NULL,
  `encode_name` varchar(255) NOT NULL,
  `parent_id` varchar(255) NOT NULL,
  `enable` varchar(255) NOT NULL COMMENT '是否可用',
  `order_id` int(11) NOT NULL,
  `simple_spelling` varchar(255) NOT NULL COMMENT '简拼',
  `full_spelling` varchar(255) NOT NULL COMMENT '全拼',
  `extend_a` varchar(255) DEFAULT NULL COMMENT '扩展字段',
  `extend_b` varchar(255) DEFAULT NULL COMMENT '扩展字段',
  `extend_c` varchar(255) DEFAULT NULL COMMENT '扩展字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统编码表';

-- ----------------------------
-- Records of sys_encode
-- ----------------------------
INSERT INTO `sys_encode` VALUES ('1', 'YN', '是否', '1', '是', '0', '0', '1', 's', 'shi', null, null, null);
INSERT INTO `sys_encode` VALUES ('2', 'YN', '是否', '0', '否', '0', '0', '2', 'f', 'fou', null, null, null);
