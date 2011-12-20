/*
MySQL Data Transfer
Source Host: localhost
Source Database: l1jdb
Target Host: localhost
Target Database: l1jdb
Date: 2011-12-20 ÏÂÎç 11:35:28
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for z_copy_polymorphs
-- ----------------------------
CREATE TABLE `z_copy_polymorphs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `æ³¨è§£` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `polyid` int(11) DEFAULT NULL,
  `minlevel` int(11) DEFAULT NULL,
  `weaponequip` int(11) DEFAULT NULL,
  `armorequip` int(11) DEFAULT NULL,
  `isSkillUse` int(11) DEFAULT NULL,
  `cause` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8721 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
