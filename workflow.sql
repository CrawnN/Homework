/*
Navicat MySQL Data Transfer

Source Server         : Crawn
Source Server Version : 50555
Source Host           : localhost:3306
Source Database       : workflow

Target Server Type    : MYSQL
Target Server Version : 50555
File Encoding         : 65001

Date: 2018-08-13 00:21:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for check_flow
-- ----------------------------
DROP TABLE IF EXISTS `check_flow`;
CREATE TABLE `check_flow` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `sponsor` int(11) NOT NULL,
  `checkman` int(11) NOT NULL,
  `directleader` int(11) NOT NULL,
  `title` varchar(30) NOT NULL,
  `type` int(5) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `postdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `check1` int(2) DEFAULT '0',
  `check2` int(2) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `sponsor` (`sponsor`),
  KEY `checkman` (`checkman`),
  KEY `directleader` (`directleader`),
  KEY `type` (`type`),
  CONSTRAINT `check_flow_ibfk_1` FOREIGN KEY (`sponsor`) REFERENCES `employee` (`id`),
  CONSTRAINT `check_flow_ibfk_2` FOREIGN KEY (`checkman`) REFERENCES `employee` (`id`),
  CONSTRAINT `check_flow_ibfk_3` FOREIGN KEY (`directleader`) REFERENCES `employee` (`id`),
  CONSTRAINT `check_flow_ibfk_4` FOREIGN KEY (`type`) REFERENCES `workflowtype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of check_flow
-- ----------------------------

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(5) NOT NULL,
  `department` varchar(40) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', 'Manager Department', null);
INSERT INTO `department` VALUES ('2', 'Auditing Department', null);
INSERT INTO `department` VALUES ('3', 'Product Department', null);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `sex` int(1) DEFAULT NULL COMMENT '0:男 1:女',
  `title` int(5) NOT NULL,
  `department` int(5) NOT NULL,
  `tell` varchar(20) DEFAULT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `title` (`title`),
  KEY `department` (`department`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`title`) REFERENCES `title` (`id`),
  CONSTRAINT `employee_ibfk_2` FOREIGN KEY (`department`) REFERENCES `department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', 'Crawn', '0', '1', '1', null, 'Crawn');
INSERT INTO `employee` VALUES ('2', 'Jack', '0', '2', '2', null, 'Jack');
INSERT INTO `employee` VALUES ('3', 'Rose', '1', '2', '3', null, 'Rose');
INSERT INTO `employee` VALUES ('4', 'Bob', '0', '3', '1', null, 'Bob');
INSERT INTO `employee` VALUES ('5', 'Lee', '1', '4', '2', null, 'Lee');
INSERT INTO `employee` VALUES ('6', 'Liu', '1', '5', '2', null, 'Liu');
INSERT INTO `employee` VALUES ('7', 'Liang', '1', '6', '2', null, 'Liang');
INSERT INTO `employee` VALUES ('8', 'Kun', '0', '4', '3', null, 'Kun');
INSERT INTO `employee` VALUES ('9', 'Nan', '1', '5', '3', null, 'Nan');
INSERT INTO `employee` VALUES ('10', 'Wang', '0', '6', '3', null, 'Wang');

-- ----------------------------
-- Table structure for title
-- ----------------------------
DROP TABLE IF EXISTS `title`;
CREATE TABLE `title` (
  `id` int(5) NOT NULL,
  `title` varchar(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of title
-- ----------------------------
INSERT INTO `title` VALUES ('1', 'President', null);
INSERT INTO `title` VALUES ('2', 'Vice President', null);
INSERT INTO `title` VALUES ('3', 'General Manager', null);
INSERT INTO `title` VALUES ('4', 'Manager', null);
INSERT INTO `title` VALUES ('5', 'Learder', null);
INSERT INTO `title` VALUES ('6', 'Staff', null);

-- ----------------------------
-- Table structure for title_copy
-- ----------------------------
DROP TABLE IF EXISTS `title_copy`;
CREATE TABLE `title_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of title_copy
-- ----------------------------
INSERT INTO `title_copy` VALUES ('7', 'abc');
INSERT INTO `title_copy` VALUES ('8', 'cd');
INSERT INTO `title_copy` VALUES ('22', 'crawn');
INSERT INTO `title_copy` VALUES ('3', 'General Manager');
INSERT INTO `title_copy` VALUES ('6', 'King');
INSERT INTO `title_copy` VALUES ('5', 'Learder');
INSERT INTO `title_copy` VALUES ('4', 'Manager');
INSERT INTO `title_copy` VALUES ('1', 'President');
INSERT INTO `title_copy` VALUES ('2', 'Vice President');

-- ----------------------------
-- Table structure for workflowtype
-- ----------------------------
DROP TABLE IF EXISTS `workflowtype`;
CREATE TABLE `workflowtype` (
  `id` int(5) NOT NULL,
  `type` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of workflowtype
-- ----------------------------
INSERT INTO `workflowtype` VALUES ('1', 'Finance');
INSERT INTO `workflowtype` VALUES ('2', 'Project approval');

-- ----------------------------
-- View structure for aa
-- ----------------------------
DROP VIEW IF EXISTS `aa`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `aa` AS select `title_copy`.`id` AS `id`,`title_copy`.`title` AS `title` from `title_copy` where (`title_copy`.`id` < 5) ;
