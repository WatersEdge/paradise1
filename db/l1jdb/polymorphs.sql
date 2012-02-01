/*
MySQL Data Transfer
Source Host: localhost
Source Database: l1jdb
Target Host: localhost
Target Database: l1jdb
Date: 2012-2-1 ���� 01:44:21
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for polymorphs
-- ----------------------------
CREATE TABLE `polymorphs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `注解` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `polyid` int(11) DEFAULT NULL,
  `minlevel` int(11) DEFAULT NULL,
  `weaponequip` int(11) DEFAULT NULL,
  `armorequip` int(11) DEFAULT NULL,
  `isSkillUse` int(11) DEFAULT NULL,
  `cause` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8720 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `polymorphs` VALUES ('29', '漂浮之眼', 'floating eye', '29', '1', '0', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('146', '杨果里恩', 'ungoliant', '146', '10', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('945', '牛', 'milkcow', '945', '1', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('947', '鹿', 'deer', '947', '1', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('951', '希比罗', 'cerberus', '951', '15', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('979', '野猪', 'wild boar', '979', '1', '0', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('1037', '巨蚁', 'giant ant', '1037', '1', '0', '136', '0', '7');
INSERT INTO `polymorphs` VALUES ('1039', '巨大兵蚁', 'giant ant soldier', '1039', '1', '0', '136', '0', '7');
INSERT INTO `polymorphs` VALUES ('1047', '毒蝎', 'scorpion', '1047', '15', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('2323', '妖魔巡守', 'orc scout polymorph', '2323', '15', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2374', '骷髅', 'skeleton polymorph', '2374', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2376', '骷髅斧手', 'skeleton axeman polymorph', '2376', '10', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2377', '骷髅枪兵', 'skeleton pike polymorph', '2377', '10', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2378', '史巴托', 'spartoi polymorph', '2378', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2384', '思克巴', 'succubus morph', '2384', '40', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2385', '雪怪', 'yeti morph', '2385', '15', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2386', '巨斧牛人', 'minotaur i morph', '2386', '15', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2387', '巨人', 'giant a morph', '2387', '15', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3101', '克特', 'black knight chief morph', '3101', '51', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3102', '巨大牛人', 'great minotaur morph', '3102', '50', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3126', '火焰弓箭手', 'fire bowman morph', '3126', '51', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3630', '独眼巨人', 'cyclops', '3630', '40', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3631', '格利芬', 'griffon', '3631', '40', '0', '32', '1', '7');
INSERT INTO `polymorphs` VALUES ('3632', '亚力安', 'cockatrice', '3632', '40', '0', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3633', '阿鲁巴', 'ettin', '3633', '40', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3634', '刺客', 'assassin', '3634', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3860', '妖魔弓箭手', 'bow orc', '3860', '1', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3861', '哥布林', 'goblin', '3861', '1', '751', '913', '0', '7');
INSERT INTO `polymorphs` VALUES ('3862', '地灵', 'kobolds', '3862', '1', '751', '913', '1', '7');
INSERT INTO `polymorphs` VALUES ('3863', '侏儒', 'dwarf', '3863', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3864', '妖魔斗士', 'orc fighter', '3864', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3865', '狼人', 'werewolf', '3865', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3866', '甘地妖魔', 'gandi orc', '3866', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3868', '阿吐巴妖魔', 'atuba orc', '3868', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3869', '都达玛拉妖魔', 'dudamara orc', '3869', '10', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3871', '骷髅弓箭手', 'skeleton archer polymorph', '3871', '10', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3873', '食尸鬼', 'ghoul', '3873', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3874', '莱肯', 'lycanthrope', '3874', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3875', '卡司特', 'ghast', '3875', '10', '751', '945', '1', '7');
INSERT INTO `polymorphs` VALUES ('3876', '食人妖精', 'bugbear', '3876', '10', '751', '945', '1', '7');
INSERT INTO `polymorphs` VALUES ('3877', '欧吉', 'ogre', '3877', '15', '1791', '913', '1', '7');
INSERT INTO `polymorphs` VALUES ('3878', '多罗', 'troll', '3878', '15', '751', '545', '1', '7');
INSERT INTO `polymorphs` VALUES ('3879', '长者', 'elder', '3879', '15', '751', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('3880', '食人妖精王', 'king bugbear', '3880', '15', '751', '945', '1', '7');
INSERT INTO `polymorphs` VALUES ('3881', '黑长者', 'dark elder', '3881', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3882', '巴土瑟', 'necromancer1', '3882', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3883', '卡士伯', 'necromancer2', '3883', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3884', '马库尔', 'necromancer3', '3884', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3885', '西玛', 'necromancer4', '3885', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3886', '小恶魔', 'lesser demon', '3886', '45', '751', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('3887', '黑暗妖精运送员', 'darkelf carrier', '3887', '45', '1791', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('3888', '巴风特', 'baphomet', '3888', '50', '751', '954', '1', '7');
INSERT INTO `polymorphs` VALUES ('3889', '恶魔', 'demon', '3889', '51', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3904', '高仑石头怪', 'stone golem', '3904', '1', '751', '145', '1', '7');
INSERT INTO `polymorphs` VALUES ('3905', '巴列斯', 'beleth', '3905', '50', '751', '954', '1', '7');
INSERT INTO `polymorphs` VALUES ('3906', '妖魔', 'orc', '3906', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3945', '果冻怪', 'gelatincube', '3945', '15', '751', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('3950', '欧姆民兵', 'middle oum', '3950', '15', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4767', '曼波兔', 'rabbit', '4767', '1', '0', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('4769', '曼波兔', 'jev rabbit', '4769', '1', '0', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('4917', '黑暗妖精巡守', 'darkelf ranger morph', '4917', '45', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4918', '强盗', 'bandit bow morph', '4918', '40', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4919', '黑暗妖精警卫（弓）', 'darkelf guard morph', '4919', '50', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4920', '艾尔摩将军', 'elmor general morph', '4920', '45', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4922', '铠甲守护神', 'guardian armor morph', '4922', '50', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4923', '黑骑士', 'black knight morph', '4923', '51', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4924', '黑暗妖精警卫（矛）', 'darkelf spear morph', '4924', '50', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4925', '艾尔摩士兵', 'elmor soldier morph', '4925', '40', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4926', '黑暗妖精法师', 'darkelf wizard morph', '4926', '50', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4928', '柯利', 'jev collie', '4928', '1', '0', '2', '1', '7');
INSERT INTO `polymorphs` VALUES ('4929', '浣熊', 'jev raccoon', '4929', '1', '0', '2', '1', '7');
INSERT INTO `polymorphs` VALUES ('5719', '招财猫', 'jev manekineko', '5719', '1', '2047', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6136', '炎魔', 'barlog 52', '6136', '52', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6137', '死亡骑士', 'death 52', '6137', '52', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6138', '刺客首领', 'assassin 52', '6138', '52', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6139', '黑暗妖精将军', 'general 52', '6139', '52', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6140', '黑暗精灵', 'darkelf 52', '6140', '52', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6141', '炎魔', 'barlog 55', '6141', '55', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6142', '死亡骑士', 'death 55', '6142', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6143', '刺客首领', 'assassin 55', '6143', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6144', '黑暗妖精将军', 'general 55', '6144', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6145', '黑暗精灵', 'darkelf 55', '6145', '55', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6146', '炎魔', 'barlog 60', '6146', '60', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6148', '刺客首领', 'assassin 60', '6148', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6149', '黑暗妖精将军', 'general 60', '6149', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6150', '黑暗精灵', 'darkelf 60', '6150', '60', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6151', '炎魔', 'barlog 65', '6151', '65', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6153', '刺客首领', 'assassin 65', '6153', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6154', '黑暗妖精将军', 'general 65', '6154', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6155', '黑暗精灵', 'darkelf 65', '6155', '65', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6156', '炎魔', 'barlog 70', '6156', '70', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6158', '刺客首领', 'assassin 70', '6158', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6159', '黑暗妖精将军', 'general 70', '6159', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6160', '黑暗精灵', 'darkelf 70', '6160', '70', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6267', '黑暗骑士', 'neo black knight', '6267', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6268', '黑暗魔导师', 'neo black mage', '6268', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6269', '黑暗巡守', 'neo black scouter', '6269', '55', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6270', '银骑士', 'neo silver knight', '6270', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6271', '银魔导师', 'neo silver mage', '6271', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6272', '银巡守', 'neo silver scouter', '6272', '60', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6273', '剑之领主', 'neo gold knight', '6273', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6274', '卫嘉德尔领主', 'neo gold mage', '6274', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6275', '箭之领主', 'neo gold scouter', '6275', '65', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6276', '亚克骑士', 'neo platinum knight', '6276', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6277', '亚克法师', 'neo platinum mage', '6277', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6278', '亚克侦察员', 'neo platinum scouter', '6278', '70', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6279', '黑暗之影', 'neo black assassin', '6279', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6282', '亚克暗影', 'neo platinum assassin', '6282', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7332', '兰斯领主', 'spearm 52', '7332', '52', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7338', '兰斯领主', 'spearm 55', '7338', '55', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7339', '兰斯领主', 'spearm 60', '7339', '60', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7340', '兰斯领主', 'spearm 65', '7340', '65', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7341', '兰斯领主', 'spearm 70', '7341', '70', '1080', '4095', '1', '7');
