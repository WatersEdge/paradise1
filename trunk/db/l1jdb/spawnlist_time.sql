/*
MySQL Data Transfer
Source Host: localhost
Source Database: l1jdb
Target Host: localhost
Target Database: l1jdb
Date: 2011-12-19 ÏÂÎç 06:32:47
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for spawnlist_time
-- ----------------------------
CREATE TABLE `spawnlist_time` (
  `spawn_id` int(11) NOT NULL,
  `time_start` time DEFAULT NULL,
  `time_end` time DEFAULT NULL,
  `delete_at_endtime` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`spawn_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `spawnlist_time` VALUES ('62086', '18:00:00', '04:00:00', '1');
INSERT INTO `spawnlist_time` VALUES ('62087', '18:00:00', '04:00:00', '1');
INSERT INTO `spawnlist_time` VALUES ('62088', '18:00:00', '04:00:00', '1');
INSERT INTO `spawnlist_time` VALUES ('62089', '18:00:00', '04:00:00', '1');
INSERT INTO `spawnlist_time` VALUES ('62092', '18:00:00', '04:00:00', '1');
