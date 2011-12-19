/*
MySQL Data Transfer
Source Host: localhost
Source Database: l1jdb
Target Host: localhost
Target Database: l1jdb
Date: 2011-12-19 ���� 06:26:33
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for castle
-- ----------------------------
CREATE TABLE `castle` (
  `castle_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL DEFAULT '',
  `war_time` datetime DEFAULT NULL,
  `tax_rate` int(11) NOT NULL DEFAULT '0',
  `public_money` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`castle_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `castle` VALUES ('1', '肯特城', '2011-12-23 18:07:57', '10', '0');
INSERT INTO `castle` VALUES ('2', '妖魔城', '2011-12-23 18:07:57', '10', '0');
INSERT INTO `castle` VALUES ('3', '风木城', '2011-12-23 18:07:57', '10', '0');
INSERT INTO `castle` VALUES ('4', '奇岩城', '2011-12-23 18:07:57', '10', '0');
INSERT INTO `castle` VALUES ('5', '海音城', '2011-12-23 18:07:57', '10', '0');
INSERT INTO `castle` VALUES ('6', '侏儒城', '2011-12-23 18:07:57', '10', '0');
INSERT INTO `castle` VALUES ('7', '亚丁城', '2011-12-23 18:07:57', '10', '0');
INSERT INTO `castle` VALUES ('8', '狄亚得要塞', '2011-12-23 18:07:57', '10', '0');
