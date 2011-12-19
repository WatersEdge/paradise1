/*
MySQL Data Transfer
Source Host: localhost
Source Database: l1jdb
Target Host: localhost
Target Database: l1jdb
Date: 2011-12-19 обнГ 06:27:08
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for character_config
-- ----------------------------
CREATE TABLE `character_config` (
  `object_id` int(10) NOT NULL DEFAULT '0',
  `length` int(10) unsigned NOT NULL DEFAULT '0',
  `data` blob,
  PRIMARY KEY (`object_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
