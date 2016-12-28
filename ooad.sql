/*
 Navicat MySQL Data Transfer

 Source Server         : MySQL
 Source Server Version : 50717
 Source Host           : localhost
 Source Database       : ooad

 Target Server Version : 50717
 File Encoding         : utf-8

 Date: 12/28/2016 20:35:27 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `backup`
-- ----------------------------
DROP TABLE IF EXISTS `backup`;
CREATE TABLE `backup` (
  `backupID` int(11) NOT NULL AUTO_INCREMENT,
  `backupName` varchar(255) CHARACTER SET utf8 NOT NULL,
  `datePurchased` date NOT NULL,
  `dateDiscarded` date DEFAULT NULL,
  `isInstalled` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`backupID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `employee`
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `employeeID` int(11) NOT NULL AUTO_INCREMENT,
  `employeeName` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`employeeID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `equipment`
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment` (
  `equipmentID` int(11) NOT NULL AUTO_INCREMENT,
  `quipmentName` varchar(255) CHARACTER SET utf8 NOT NULL,
  `datePurchased` date NOT NULL,
  `dateDiscarded` date DEFAULT NULL,
  `isBorrowed` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`equipmentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `recordBackup`
-- ----------------------------
DROP TABLE IF EXISTS `recordBackup`;
CREATE TABLE `recordBackup` (
  `recordBackupID` int(11) NOT NULL AUTO_INCREMENT,
  `backupID` int(11) NOT NULL,
  `equipmentID` int(11) NOT NULL,
  `employeeID` int(11) NOT NULL,
  `dateBorrowed` date NOT NULL,
  `dateReturned` date DEFAULT NULL,
  PRIMARY KEY (`recordBackupID`),
  KEY `backupID` (`backupID`),
  KEY `equipmentID` (`equipmentID`),
  KEY `employeeID` (`employeeID`),
  CONSTRAINT `BackupEmployeeID` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `BackupEquipmentID` FOREIGN KEY (`equipmentID`) REFERENCES `equipment` (`equipmentID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `backupID` FOREIGN KEY (`backupID`) REFERENCES `backup` (`backupID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `recordEquipment`
-- ----------------------------
DROP TABLE IF EXISTS `recordEquipment`;
CREATE TABLE `recordEquipment` (
  `recordEquipmentID` int(11) NOT NULL,
  `equipmentID` int(11) NOT NULL,
  `employeeID` int(11) NOT NULL,
  `dateBorrowed` date NOT NULL,
  `dateReturned` date DEFAULT NULL,
  PRIMARY KEY (`recordEquipmentID`),
  KEY `equipmentID` (`equipmentID`),
  KEY `employeeID` (`employeeID`),
  CONSTRAINT `equipmentEquipmentID` FOREIGN KEY (`equipmentID`) REFERENCES `equipment` (`equipmentID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `equipmenteEmployeeID` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
