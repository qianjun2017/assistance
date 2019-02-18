-- MySQL dump 10.13  Distrib 8.0.11, for macos10.13 (x86_64)
--
-- Host: localhost    Database: assistance
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_system_auth`
--

DROP TABLE IF EXISTS `t_system_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_system_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authCode` varchar(64) DEFAULT NULL,
  `authName` varchar(64) DEFAULT NULL,
  `authType` varchar(16) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `level` smallint(6) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_auth`
--

LOCK TABLES `t_system_auth` WRITE;
/*!40000 ALTER TABLE `t_system_auth` DISABLE KEYS */;
INSERT INTO `t_system_auth` VALUES (1,'system.user','人员管理','menu',NULL,0,'2018-03-06 09:09:08'),(2,'system.user.add','新增人员','oper',1,1,'2018-03-06 09:09:32'),(3,'system.user.edit','编辑人员','oper',1,0,'2018-03-06 09:09:49'),(4,'system.user.delete','删除人员','oper',1,1,'2018-03-06 09:10:01'),(5,'system.role','角色管理','menu',NULL,0,'2018-03-06 09:10:54'),(6,'system.role.add','新增角色','oper',5,1,'2018-03-06 09:12:07'),(7,'system.role.edit','编辑角色','oper',5,0,'2018-03-06 09:12:17'),(8,'system.role.delete','删除角色','oper',5,1,'2018-03-06 09:12:27'),(9,'system.auth','权限管理','menu',NULL,0,'2018-03-06 09:13:40'),(10,'system.auth.add','新增权限','oper',9,1,'2018-03-06 09:14:12'),(11,'system.auth.update','修改权限','oper',9,1,'2018-03-06 09:14:23'),(12,'system.auth.delete','删除权限','oper',9,1,'2018-03-06 09:14:37'),(13,'system.auth.detail','查看权限','oper',9,1,'2018-08-23 01:15:00'),(14,'system.user.authorize','人员授权','oper',1,1,'2018-08-23 01:16:49'),(15,'system.role.authorize','角色授权','oper',5,1,'2018-08-23 01:17:34');
/*!40000 ALTER TABLE `t_system_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_location`
--

DROP TABLE IF EXISTS `t_system_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_system_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `locationCode` varchar(32) NOT NULL,
  `locationName` varchar(512) NOT NULL,
  `locationCodePath` varchar(512) NOT NULL,
  `locationNamePath` varchar(512) NOT NULL,
  `parentId` int(11) DEFAULT NULL,
  `level` smallint(6) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_location`
--

LOCK TABLES `t_system_location` WRITE;
/*!40000 ALTER TABLE `t_system_location` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_system_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_operation_log`
--

DROP TABLE IF EXISTS `t_system_operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_system_operation_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clientIp` varchar(16) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `userName` varchar(64) DEFAULT NULL,
  `params` varchar(1024) DEFAULT NULL,
  `module` varchar(32) DEFAULT NULL,
  `operType` varchar(32) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL,
  `result` varchar(256) DEFAULT NULL,
  `time` int(11) DEFAULT NULL,
  `title` varchar(128) DEFAULT NULL,
  `operateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_operation_log`
--

LOCK TABLES `t_system_operation_log` WRITE;
/*!40000 ALTER TABLE `t_system_operation_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_system_operation_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_role`
--

DROP TABLE IF EXISTS `t_system_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_system_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(64) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_role`
--

LOCK TABLES `t_system_role` WRITE;
/*!40000 ALTER TABLE `t_system_role` DISABLE KEYS */;
INSERT INTO `t_system_role` VALUES (1,'超级管理员','2018-04-13 13:58:21');
/*!40000 ALTER TABLE `t_system_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_role_auth`
--

DROP TABLE IF EXISTS `t_system_role_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_system_role_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `authId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_role_auth`
--

LOCK TABLES `t_system_role_auth` WRITE;
/*!40000 ALTER TABLE `t_system_role_auth` DISABLE KEYS */;
INSERT INTO `t_system_role_auth` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9),(10,1,10),(11,1,11),(12,1,12),(13,1,13),(14,1,14),(15,1,15);
/*!40000 ALTER TABLE `t_system_role_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_user`
--

DROP TABLE IF EXISTS `t_system_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_system_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(32) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `nickName` varchar(64) DEFAULT NULL,
  `realName` varchar(32) DEFAULT NULL,
  `salt` varchar(16) DEFAULT NULL,
  `openId` varchar(128) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `orgId` int(11) DEFAULT NULL,
  `imageUrl` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_user`
--

LOCK TABLES `t_system_user` WRITE;
/*!40000 ALTER TABLE `t_system_user` DISABLE KEYS */;
INSERT INTO `t_system_user` VALUES (1,'admin','3d1463e279bd9c5277df283bcc2aa545','超级管理员','袁文树','tJbpS',NULL,'15086920153','2018-04-13 13:44:01',NULL,NULL);
/*!40000 ALTER TABLE `t_system_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_user_role`
--

DROP TABLE IF EXISTS `t_system_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `t_system_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_user_role`
--

LOCK TABLES `t_system_user_role` WRITE;
/*!40000 ALTER TABLE `t_system_user_role` DISABLE KEYS */;
INSERT INTO `t_system_user_role` VALUES (1,1,1);
/*!40000 ALTER TABLE `t_system_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_carousel`
--

DROP TABLE IF EXISTS `t_carousel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_carousel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `imageUrl` varchar(128) DEFAULT NULL,
  `status` varchar(16) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `path` varchar(16) DEFAULT NULL,
  `clicked` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_carousel`
--

LOCK TABLES `t_carousel` WRITE;
/*!40000 ALTER TABLE `t_carousel` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_carousel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_interface`
--

DROP TABLE IF EXISTS `t_interface`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_interface` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `code` varchar(64) NOT NULL,
  `type` varchar(8) NOT NULL,
  `request` blob,
  `response` blob,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_interface`
--

LOCK TABLES `t_interface` WRITE;
/*!40000 ALTER TABLE `t_interface` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_interface` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_customer`
--

DROP TABLE IF EXISTS `t_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickName` varchar(32) DEFAULT NULL,
  `openid` varchar(32) NOT NULL,
  `avatarUrl` varchar(128) DEFAULT NULL,
  `status` varchar(16) NOT NULL,
  `createTime` datetime NOT NULL,
  `retailer` bit(1) DEFAULT NULL,
  `store` varchar(128) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `address` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_customer`
--

LOCK TABLES `t_customer` WRITE;
/*!40000 ALTER TABLE `t_customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_lottery`
--

DROP TABLE IF EXISTS `t_lottery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_lottery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerId` int(11) NOT NULL,
  `no` int(11) NOT NULL,
  `status` varchar(16) NOT NULL,
  `mCount` tinyint(4) NOT NULL,
  `createTime` datetime NOT NULL,
  `lastExchangeTime` datetime NOT NULL,
  `share` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_lottery`
--

LOCK TABLES `t_lottery` WRITE;
/*!40000 ALTER TABLE `t_lottery` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_lottery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_lottery_prize`
--

DROP TABLE IF EXISTS `t_lottery_prize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_lottery_prize` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lotteryId` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `total` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `weight` int(11) NOT NULL,
  `status` varchar(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_lottery_prize`
--

LOCK TABLES `t_lottery_prize` WRITE;
/*!40000 ALTER TABLE `t_lottery_prize` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_lottery_prize` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_lottery_customer`
--

DROP TABLE IF EXISTS `t_lottery_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_lottery_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerId` int(11) NOT NULL,
  `lotteryPrizeId` int(11) NOT NULL,
  `status` varchar(16) NOT NULL,
  `createTime` datetime NOT NULL,
  `exchangeTime` datetime DEFAULT NULL,
  `prize` bit(1) DEFAULT NULL,
  `share` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_lottery_customer`
--

LOCK TABLES `t_lottery_customer` WRITE;
/*!40000 ALTER TABLE `t_lottery_customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_lottery_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_config`
--

DROP TABLE IF EXISTS `t_system_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_system_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `propertyName` varchar(64) DEFAULT NULL,
  `propertyValue` varchar(512) DEFAULT NULL,
  `propertyDesc` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_config`
--

LOCK TABLES `t_system_config` WRITE;
/*!40000 ALTER TABLE `t_system_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_system_config` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-23  9:23:31
