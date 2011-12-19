/*
MySQL Data Transfer
Source Host: localhost
Source Database: l1jdb
Target Host: localhost
Target Database: l1jdb
Date: 2011-12-19 обнГ 06:27:46
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for character_quests
-- ----------------------------
CREATE TABLE `character_quests` (
  `char_id` int(10) unsigned NOT NULL,
  `quest_id` int(10) unsigned NOT NULL DEFAULT '0',
  `quest_step` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`char_id`,`quest_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
