/*
MySQL Data Transfer
Source Host: localhost
Source Database: l1jdb
Target Host: localhost
Target Database: l1jdb
Date: 2011-12-19 ÏÂÎç 06:30:31
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for log_enchant
-- ----------------------------
CREATE TABLE `log_enchant` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `char_id` int(10) NOT NULL DEFAULT '0',
  `item_id` int(10) unsigned NOT NULL DEFAULT '0',
  `old_enchantlvl` int(3) NOT NULL DEFAULT '0',
  `new_enchantlvl` int(3) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `key_id` (`char_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
