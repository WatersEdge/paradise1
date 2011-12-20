/*
MySQL Data Transfer
Source Host: localhost
Source Database: l1jdb
Target Host: localhost
Target Database: l1jdb
Date: 2011-12-20 ÏÂÎç 11:35:35
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for z_copy_skills
-- ----------------------------
CREATE TABLE `z_copy_skills` (
  `skill_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT '',
  `skill_level` int(10) NOT NULL DEFAULT '0',
  `skill_number` int(10) NOT NULL DEFAULT '0',
  `mpConsume` int(10) unsigned NOT NULL DEFAULT '0',
  `hpConsume` int(10) unsigned NOT NULL DEFAULT '0',
  `itemConsumeId` int(10) unsigned NOT NULL DEFAULT '0',
  `itemConsumeCount` int(10) unsigned NOT NULL DEFAULT '0',
  `reuseDelay` int(10) unsigned NOT NULL DEFAULT '0',
  `buffDuration` int(10) unsigned NOT NULL DEFAULT '0',
  `target` varchar(45) NOT NULL DEFAULT '',
  `target_to` int(10) NOT NULL DEFAULT '0',
  `damage_value` int(10) unsigned NOT NULL DEFAULT '0',
  `damage_dice` int(10) unsigned NOT NULL DEFAULT '0',
  `damage_dice_count` int(10) unsigned NOT NULL DEFAULT '0',
  `probability_value` int(10) unsigned NOT NULL DEFAULT '0',
  `probability_dice` int(10) unsigned NOT NULL DEFAULT '0',
  `attr` int(10) unsigned NOT NULL DEFAULT '0',
  `type` int(10) unsigned NOT NULL DEFAULT '0',
  `lawful` int(10) NOT NULL DEFAULT '0',
  `ranged` int(10) NOT NULL DEFAULT '0',
  `area` int(10) NOT NULL DEFAULT '0',
  `through` int(10) NOT NULL DEFAULT '0',
  `id` int(10) unsigned NOT NULL DEFAULT '0',
  `nameid` varchar(45) NOT NULL DEFAULT '',
  `action_id` int(10) unsigned NOT NULL DEFAULT '0',
  `castgfx` int(10) unsigned NOT NULL DEFAULT '0',
  `castgfx2` int(10) unsigned NOT NULL DEFAULT '0',
  `sysmsgID_happen` int(10) unsigned NOT NULL DEFAULT '0',
  `sysmsgID_stop` int(10) unsigned NOT NULL DEFAULT '0',
  `sysmsgID_fail` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`skill_id`)
) ENGINE=MyISAM AUTO_INCREMENT=20034 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
