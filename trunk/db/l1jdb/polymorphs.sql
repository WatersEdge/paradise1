/*
MySQL Data Transfer
Source Host: localhost
Source Database: l1jdb
Target Host: localhost
Target Database: l1jdb
Date: 2011-12-20 ÏÂÎç 11:34:50
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for polymorphs
-- ----------------------------
CREATE TABLE `polymorphs` (
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
) ENGINE=MyISAM AUTO_INCREMENT=8720 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `polymorphs` VALUES ('29', null, 'floating eye', '29', '1', '0', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('95', null, 'shelob', '95', '10', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('146', null, 'ungoliant', '146', '10', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('938', null, 'beagle', '938', '1', '0', '2', '1', '7');
INSERT INTO `polymorphs` VALUES ('945', null, 'milkcow', '945', '1', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('947', null, 'deer', '947', '1', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('951', null, 'cerberus', '951', '15', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('979', null, 'wild boar', '979', '1', '0', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('1037', null, 'giant ant', '1037', '1', '0', '136', '0', '7');
INSERT INTO `polymorphs` VALUES ('1039', null, 'giant ant soldier', '1039', '1', '0', '136', '0', '7');
INSERT INTO `polymorphs` VALUES ('1047', null, 'scorpion', '1047', '15', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('2064', null, 'snowman', '2064', '1', '0', '1027', '1', '7');
INSERT INTO `polymorphs` VALUES ('2284', null, 'dark elf polymorph', '2284', '52', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2323', null, 'orc scout polymorph', '2323', '15', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2374', null, 'skeleton polymorph', '2374', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2376', null, 'skeleton axeman polymorph', '2376', '10', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2377', null, 'skeleton pike polymorph', '2377', '10', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2378', null, 'spartoi polymorph', '2378', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2384', null, 'succubus morph', '2384', '40', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2385', null, 'yeti morph', '2385', '15', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2386', null, 'minotaur i morph', '2386', '15', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2387', null, 'giant a morph', '2387', '15', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('2388', null, 'death', '2388', '1', '0', '32', '0', '7');
INSERT INTO `polymorphs` VALUES ('2501', null, 'jack o lantern', '2501', '1', '751', '417', '0', '7');
INSERT INTO `polymorphs` VALUES ('3101', null, 'black knight chief morph', '3101', '51', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3102', null, 'great minotaur morph', '3102', '50', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3103', null, 'barlog morph', '3103', '52', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3126', null, 'fire bowman morph', '3126', '51', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3630', null, 'cyclops', '3630', '40', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3631', null, 'griffon', '3631', '40', '0', '32', '1', '7');
INSERT INTO `polymorphs` VALUES ('3632', null, 'cockatrice', '3632', '40', '0', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3633', null, 'ettin', '3633', '40', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3634', null, 'assassin', '3634', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3664', null, 'baranka', '3664', '1', '704', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3750', null, 'haregi', '3750', '1', '0', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3784', null, 'death knight', '3784', '52', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3860', null, 'bow orc', '3860', '1', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3861', null, 'goblin', '3861', '1', '751', '913', '0', '7');
INSERT INTO `polymorphs` VALUES ('3862', null, 'kobolds', '3862', '1', '751', '913', '1', '7');
INSERT INTO `polymorphs` VALUES ('3863', null, 'dwarf', '3863', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3864', null, 'orc fighter', '3864', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3865', null, 'werewolf', '3865', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3866', null, 'gandi orc', '3866', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3867', null, 'rova orc', '3867', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3868', null, 'atuba orc', '3868', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3869', null, 'dudamara orc', '3869', '10', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3870', null, 'neruga orc', '3870', '10', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3871', null, 'skeleton archer polymorph', '3871', '10', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3872', null, 'zombie', '3872', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3873', null, 'ghoul', '3873', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3874', null, 'lycanthrope', '3874', '10', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3875', null, 'ghast', '3875', '10', '751', '945', '1', '7');
INSERT INTO `polymorphs` VALUES ('3876', null, 'bugbear', '3876', '10', '751', '945', '1', '7');
INSERT INTO `polymorphs` VALUES ('3877', null, 'ogre', '3877', '15', '1791', '913', '1', '7');
INSERT INTO `polymorphs` VALUES ('3878', null, 'troll', '3878', '15', '751', '545', '1', '7');
INSERT INTO `polymorphs` VALUES ('3879', null, 'elder', '3879', '15', '751', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('3880', null, 'king bugbear', '3880', '15', '751', '945', '1', '7');
INSERT INTO `polymorphs` VALUES ('3881', null, 'dark elder', '3881', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3882', null, 'necromancer1', '3882', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3883', null, 'necromancer2', '3883', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3884', null, 'necromancer3', '3884', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3885', null, 'necromancer4', '3885', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3886', null, 'lesser demon', '3886', '45', '751', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('3887', null, 'darkelf carrier', '3887', '45', '1791', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('3888', null, 'baphomet', '3888', '50', '751', '954', '1', '7');
INSERT INTO `polymorphs` VALUES ('3889', null, 'demon', '3889', '51', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3890', null, 'ancient black knight morph', '3890', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3891', null, 'ancient black mage morph', '3891', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3892', null, 'ancient black scouter morph', '3892', '55', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3893', null, 'ancient silver knight morph', '3893', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3894', null, 'ancient silver mage morph', '3894', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3895', null, 'ancient silver scouter morph', '3895', '60', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3896', null, 'ancient gold knight morph', '3896', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3897', null, 'ancient gold mage morph', '3897', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3898', null, 'ancient gold scouter morph', '3898', '65', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3899', null, 'ancient platinum knight morph', '3899', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3900', null, 'ancient platinum mage morph', '3900', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3901', null, 'ancient platinum scouter morph', '3901', '70', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3902', null, 'Kelenis Morph', '3902', '1', '43', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3903', null, 'Ken Lauhel Morph', '3903', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3904', null, 'stone golem', '3904', '1', '751', '145', '1', '7');
INSERT INTO `polymorphs` VALUES ('3905', null, 'beleth', '3905', '50', '751', '954', '1', '7');
INSERT INTO `polymorphs` VALUES ('3906', null, 'orc', '3906', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3945', null, 'gelatincube', '3945', '15', '751', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('3950', null, 'middle oum', '3950', '15', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('3952', null, 'vampire', '3952', '1', '0', '32', '0', '7');
INSERT INTO `polymorphs` VALUES ('4000', null, 'knight vald morph', '4000', '1', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4001', null, 'iris morph', '4001', '1', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4002', null, 'paperman morph', '4002', '1', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4004', null, 'succubus queen morph', '4004', '1', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4186', null, 'robber bone', '4186', '1', '751', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('4188', null, 'robber bone head', '4188', '1', '751', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('4190', null, 'robber bone soldier', '4190', '1', '256', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('4227', null, 'hakama', '4227', '1', '0', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4767', null, 'rabbit', '4767', '1', '0', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('4769', null, 'carrot rabbit', '4769', '1', '0', '4095', '0', '7');
INSERT INTO `polymorphs` VALUES ('4917', null, 'darkelf ranger morph', '4917', '45', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4918', null, 'bandit bow morph', '4918', '40', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4919', null, 'darkelf guard morph', '4919', '50', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4920', null, 'elmor general morph', '4920', '45', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4921', null, 'darkelf general morph', '4921', '52', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4922', null, 'guardian armor morph', '4922', '50', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4923', null, 'black knight morph', '4923', '51', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4924', null, 'darkelf spear morph', '4924', '50', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4925', null, 'elmor soldier morph', '4925', '40', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4926', null, 'darkelf wizard morph', '4926', '50', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('4928', null, 'high collie', '4928', '1', '0', '2', '1', '7');
INSERT INTO `polymorphs` VALUES ('4929', null, 'high raccoon', '4929', '1', '0', '2', '1', '7');
INSERT INTO `polymorphs` VALUES ('4932', null, 'assassin master morph', '4932', '52', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('5184', null, 'red uniform', '5184', '1', '0', '8', '1', '7');
INSERT INTO `polymorphs` VALUES ('5186', null, 'blue uniform', '5186', '1', '0', '8', '1', '7');
INSERT INTO `polymorphs` VALUES ('5645', null, 'Halloween Pumpkin', '5645', '1', '2047', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('5719', null, 'manekineko', '5719', '1', '2047', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('5727', null, 'ancient black assassin morph', '5727', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('5730', null, 'ancient silver assassin morph', '5730', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('5733', null, 'ancient gold assassin morph', '5733', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('5736', null, 'ancient platinum assassin morph', '5736', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('5976', null, 'high bear', '5976', '1', '751', '2', '0', '7');
INSERT INTO `polymorphs` VALUES ('6002', null, 'spirit of earth boss', '6002', '1', '8', '0', '1', '7');
INSERT INTO `polymorphs` VALUES ('6010', null, 'red orc', '6010', '1', '0', '1', '0', '7');
INSERT INTO `polymorphs` VALUES ('6034', null, 'priest of corruption', '6034', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6035', null, 'quest lesser demon', '6035', '45', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6080', null, 'princess horse', '6080', '1', '16', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6086', null, 'Rabor Born Head', '6086', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6087', null, 'Rabor Born archer', '6087', '1', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6088', null, 'Rabor Born knife', '6088', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6089', null, 'drake morph', '6089', '1', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6094', null, 'prince horse', '6094', '1', '16', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6136', null, 'barlog 52', '6136', '52', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6137', null, 'death 52', '6137', '52', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6138', null, 'assassin 52', '6138', '52', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6139', null, 'general 52', '6139', '52', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6140', null, 'darkelf 52', '6140', '52', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6141', null, 'barlog 55', '6141', '55', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6142', null, 'death 55', '6142', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6143', null, 'assassin 55', '6143', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6144', null, 'general 55', '6144', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6145', null, 'darkelf 55', '6145', '55', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6146', null, 'barlog 60', '6146', '60', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6147', null, 'death 60', '6147', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6148', null, 'assassin 60', '6148', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6149', null, 'general 60', '6149', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6150', null, 'darkelf 60', '6150', '60', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6151', null, 'barlog 65', '6151', '65', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6152', null, 'death 65', '6152', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6153', null, 'assassin 65', '6153', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6154', null, 'general 65', '6154', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6155', null, 'darkelf 65', '6155', '65', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6156', null, 'barlog 70', '6156', '70', '1791', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6157', null, 'death 70', '6157', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6158', null, 'assassin 70', '6158', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6159', null, 'general 70', '6159', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6160', null, 'darkelf 70', '6160', '70', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6180', null, 'unicorn A', '6180', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6181', null, 'unicorn B', '6181', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6182', null, 'unicorn C', '6182', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6183', null, 'unicorn D', '6183', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6184', null, 'bear A', '6184', '0', '749', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6185', null, 'bear B', '6185', '0', '749', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6186', null, 'bear C', '6186', '0', '749', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6187', null, 'bear D', '6187', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6188', null, 'mini white dog A', '6188', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6189', null, 'mini white dog B', '6189', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6190', null, 'mini white dog C', '6190', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6191', null, 'mini white dog D', '6191', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6192', null, 'ratman A', '6192', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6193', null, 'ratman B', '6193', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6194', null, 'ratman C', '6194', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6195', null, 'ratman D', '6195', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6196', null, 'pet tiger A', '6196', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6197', null, 'pet tiger B', '6197', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6198', null, 'pet tiger C', '6198', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6199', null, 'pet tiger D', '6199', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6200', null, 'dillo A', '6200', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6201', null, 'dillo B', '6201', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6202', null, 'dillo C', '6202', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6203', null, 'dillo D', '6203', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6204', null, 'mole A', '6204', '0', '256', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6205', null, 'mole B', '6205', '0', '256', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6206', null, 'mole C', '6206', '0', '256', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6207', null, 'mole D', '6207', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6208', null, 'darkelf thief A', '6208', '0', '1007', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6209', null, 'darkelf thief B', '6209', '0', '1007', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6210', null, 'darkelf thief C', '6210', '0', '1007', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6211', null, 'darkelf thief D', '6211', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6212', null, 'ken lauhel A', '6212', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6213', null, 'ken lauhel B', '6213', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6214', null, 'ken lauhel C', '6214', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6215', null, 'ken lauhel D', '6215', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6216', null, 'kelenis A', '6216', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6217', null, 'kelenis B', '6217', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6218', null, 'kelenis C', '6218', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6219', null, 'kelenis D', '6219', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6220', null, 'slave A', '6220', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6221', null, 'slave B', '6221', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6222', null, 'slave C', '6222', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6223', null, 'slave D', '6223', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6224', null, 'dofleganger boss A', '6224', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6225', null, 'dofleganger boss B', '6225', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6226', null, 'dofleganger boss C', '6226', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6227', null, 'dofleganger boss D', '6227', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6228', null, 'lich A', '6228', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6229', null, 'lich B', '6229', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6230', null, 'lich C', '6230', '0', '751', '4095', '1', '10');
INSERT INTO `polymorphs` VALUES ('6231', null, 'lich D', '6231', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6232', null, 'woman1 A', '6232', '0', '0', '4095', '0', '10');
INSERT INTO `polymorphs` VALUES ('6233', null, 'woman1 B', '6233', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6234', null, 'woman2 A', '6234', '0', '0', '4095', '0', '10');
INSERT INTO `polymorphs` VALUES ('6235', null, 'woman2 B', '6235', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6236', null, 'woman3 A', '6236', '0', '0', '4095', '0', '10');
INSERT INTO `polymorphs` VALUES ('6237', null, 'woman3 B', '6237', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6238', null, 'woman4 A', '6238', '0', '0', '4095', '0', '10');
INSERT INTO `polymorphs` VALUES ('6239', null, 'woman4 B', '6239', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6240', null, 'woman5 A', '6240', '0', '0', '4095', '0', '10');
INSERT INTO `polymorphs` VALUES ('6241', null, 'woman5 B', '6241', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6242', null, 'noblewoman A', '6242', '0', '0', '4095', '0', '10');
INSERT INTO `polymorphs` VALUES ('6243', null, 'noblewoman B', '6243', '0', '0', '0', '0', '10');
INSERT INTO `polymorphs` VALUES ('6267', null, 'neo black knight', '6267', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6268', null, 'neo black mage', '6268', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6269', null, 'neo black scouter', '6269', '55', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6270', null, 'neo silver knight', '6270', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6271', null, 'neo silver mage', '6271', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6272', null, 'neo silver scouter', '6272', '60', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6273', null, 'neo gold knight', '6273', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6274', null, 'neo gold mage', '6274', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6275', null, 'neo gold scouter', '6275', '65', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6276', null, 'neo platinum knight', '6276', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6277', null, 'neo platinum mage', '6277', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6278', null, 'neo platinum scouter', '6278', '70', '256', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6279', null, 'neo black assassin', '6279', '55', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6280', null, 'neo silver assassin', '6280', '60', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6281', null, 'neo gold assassin', '6281', '65', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6282', null, 'neo platinum assassin', '6282', '70', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6284', null, 'Haunted House jack o lantern', '6284', '1', '0', '0', '0', '7');
INSERT INTO `polymorphs` VALUES ('6400', null, 'Halloween jack o lantern', '6400', '1', '2047', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('6894', null, 'awake', '6894', '1', '751', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7332', null, 'spearm 52', '7332', '52', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7338', null, 'spearm 55', '7338', '55', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7339', null, 'spearm 60', '7339', '60', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7340', null, 'spearm 65', '7340', '65', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('7341', null, 'spearm 70', '7341', '70', '1080', '4095', '1', '7');
INSERT INTO `polymorphs` VALUES ('8719', null, 'æŸ‘æ©˜', '8719', '1', '2047', '4095', '1', '7');
