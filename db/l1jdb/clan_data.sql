/*
MySQL Data Transfer
Source Host: localhost
Source Database: l1jdb
Target Host: localhost
Target Database: l1jdb
Date: 2011-12-19 ���� 06:28:42
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for clan_data
-- ----------------------------
CREATE TABLE `clan_data` (
  `clan_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `clan_name` varchar(45) NOT NULL DEFAULT '',
  `leader_id` int(10) unsigned NOT NULL DEFAULT '0',
  `leader_name` varchar(45) NOT NULL DEFAULT '',
  `hascastle` int(10) unsigned NOT NULL DEFAULT '0',
  `hashouse` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`clan_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
