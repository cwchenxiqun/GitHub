CREATE DATABASE  IF NOT EXISTS `armourhero` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `armourhero`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: armourhero
-- ------------------------------------------------------
-- Server version	5.7.3-m13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_gl_qx`
--

DROP TABLE IF EXISTS `sys_gl_qx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_gl_qx` (
  `GL_ID` varchar(100) NOT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `FX_QX` int(10) DEFAULT NULL,
  `FW_QX` int(10) DEFAULT NULL,
  `QX1` int(10) DEFAULT NULL,
  `QX2` int(10) DEFAULT NULL,
  `QX3` int(10) DEFAULT NULL,
  `QX4` int(10) DEFAULT NULL,
  PRIMARY KEY (`GL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_gl_qx`
--

LOCK TABLES `sys_gl_qx` WRITE;
/*!40000 ALTER TABLE `sys_gl_qx` DISABLE KEYS */;
INSERT INTO `sys_gl_qx` VALUES ('1','2',1,1,1,1,1,1),('2','1',0,0,1,1,1,1),('30e123af109e4048ba9435285d87353b','2',0,0,0,0,0,0),('55896f5ce3c0494fa6850775a4e29ff6','7',0,0,1,0,0,0),('656f5e0bbdcd4caf8295e2c16450fec1','2',0,0,0,0,0,0),('68f23fc0caee475bae8d52244dea8444','7',0,0,1,0,0,0),('7061e633751d48f185726d07b2fd405d','1',0,0,0,0,0,0),('7dfd8d1f7b6245d283217b7e63eec9b2','1',1,1,0,0,0,0),('a39f690ec7984e60850e6b02115fa14c','2',0,0,0,0,0,0),('ac66961adaa2426da4470c72ffeec117','1',1,0,0,1,0,0),('b0c77c29dfa140dc9b14a29c056f824f','7',1,0,1,0,0,0),('ce7e1da376d645caa7b26116d9318066','2',0,0,0,0,0,0),('e74f713314154c35bd7fc98897859fe3','6',1,1,1,1,0,0),('f944a9df72634249bbcb8cb73b0c9b86','7',1,1,1,0,0,0),('fa7e8ab53c434f27a2c4ed5f5a5f4124','1',0,0,0,0,0,0);
/*!40000 ALTER TABLE `sys_gl_qx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `MENU_ID` int(11) NOT NULL,
  `MENU_NAME` varchar(255) DEFAULT NULL,
  `MENU_URL` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `MENU_ORDER` varchar(100) DEFAULT NULL,
  `MENU_ICON` varchar(30) DEFAULT NULL,
  `MENU_TYPE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理','#','0','1','icon-desktop','2'),(2,'角色管理','role.do','1','2',NULL,'2'),(4,'渠道经销商用户管理','dealerUser/listUsers.do','1','4',NULL,'2'),(5,'系统用户管理','user/listUsers.do','1','3',NULL,'2'),(6,'渠道经销商数据查询','#','0','2','icon-th','2'),(15,'渠道经销商','#','0','4','icon-heart','2'),(20,'新增用户数','新增用户数','6','1',NULL,'2'),(21,'用户留存','用户留存','6','2',NULL,'2'),(22,'DAU与收入','DAU与收入','6','3',NULL,'2'),(23,'结算管理','#','0','3',NULL,''),(24,'结算管理','结算管理','23','1',NULL,''),(25,'结算设置','settlement/toSettlementSetting.do','23','2',NULL,''),(26,'渠道数据统计','渠道数据统计','6','4',NULL,'2'),(27,'新增用户数','新增用户数','15','1',NULL,'2'),(28,'结算查询','结算查询','15','2',NULL,'2'),(29,'个人信息','dealerUser/goViewU.do','15','3',NULL,'2'),(30,'系统工具','#','0','5','icon-th',''),(31,'接口测试','tool/interfaceTest.do','30','1',NULL,''),(32,'二维码','tool/goTwoDimensionCode.do','30','2',NULL,'');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `ROLE_ID` varchar(100) NOT NULL,
  `ROLE_NAME` varchar(100) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `ADD_QX` varchar(255) DEFAULT NULL,
  `DEL_QX` varchar(255) DEFAULT NULL,
  `EDIT_QX` varchar(255) DEFAULT NULL,
  `CHA_QX` varchar(255) DEFAULT NULL,
  `QX_ID` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES ('1','系统管理员','8588918902','0','1','1','1','1','1'),('2','渠道经销商','939556864','0','1048822','50','34','54','2'),('30e123af109e4048ba9435285d87353b','华北经销商','939556864','2','0','0','0','0','30e123af109e4048ba9435285d87353b'),('656f5e0bbdcd4caf8295e2c16450fec1','华东经销商','939556864','2','0','0','0','0','656f5e0bbdcd4caf8295e2c16450fec1'),('7061e633751d48f185726d07b2fd405d','管理员','8588918902','1','','0','0','1072726134','7061e633751d48f185726d07b2fd405d'),('a39f690ec7984e60850e6b02115fa14c','华南经销商','939556864','2','0','0','0','0','a39f690ec7984e60850e6b02115fa14c'),('ce7e1da376d645caa7b26116d9318066','华中经销商','939556864','2','0','0','0','0','ce7e1da376d645caa7b26116d9318066'),('fa7e8ab53c434f27a2c4ed5f5a5f4124','超级管理员','8588918902','1','1072726134','1072726134','1072726134','1072726134','fa7e8ab53c434f27a2c4ed5f5a5f4124');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_static`
--

DROP TABLE IF EXISTS `sys_static`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_static` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `STATIC_KEY` varchar(64) COLLATE utf8_bin NOT NULL,
  `STATIC_VALUE` varchar(64) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `STATIC_KEY_UNIQUE` (`STATIC_KEY`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统静态配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_static`
--

LOCK TABLES `sys_static` WRITE;
/*!40000 ALTER TABLE `sys_static` DISABLE KEYS */;
INSERT INTO `sys_static` VALUES (8,'SETTLEMENT_COEFFICIENT','0.4','结算系数'),(9,'STATIC_KEY_SETTLEMENT_PRICE','2.2','结算单价');
/*!40000 ALTER TABLE `sys_static` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `USER_ID` varchar(100) NOT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `LAST_LOGIN` varchar(255) DEFAULT NULL,
  `IP` varchar(100) DEFAULT NULL,
  `STATUS` varchar(32) DEFAULT NULL,
  `BZ` varchar(255) DEFAULT NULL,
  `SKIN` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(32) DEFAULT NULL,
  `NUMBER` varchar(100) DEFAULT NULL,
  `PHONE` varchar(32) DEFAULT NULL,
  `PROVINCE` varchar(45) DEFAULT NULL,
  `CITY` varchar(45) DEFAULT NULL,
  `DISTRICT` varchar(45) DEFAULT NULL,
  `DEALER_CODE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES ('1','admin','de41b7fb99201d8334c23c014db35ecd92df81bc','系统管理员','1133671055321055258374707980945218933803269864762743594642571294','1','2015-12-11 17:02:15','127.0.0.1','0','最高统治者','skin-3','admin@main.com',NULL,'18788888887','湖南','株洲',NULL,NULL),('d991bf237f9c4f039a6e8c4e914788ae','chenwei_1','335dda2a034f189729dca3f3778a365e06c843a4','陈卫','','656f5e0bbdcd4caf8295e2c16450fec1','','','0','爱的','default','cw1@163.com',NULL,'18651886712','江苏','南京','玄武区','121');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_qx`
--

DROP TABLE IF EXISTS `sys_user_qx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_qx` (
  `U_ID` varchar(100) NOT NULL,
  `C1` int(10) DEFAULT NULL,
  `C2` int(10) DEFAULT NULL,
  `C3` int(10) DEFAULT NULL,
  `C4` int(10) DEFAULT NULL,
  `Q1` int(10) DEFAULT NULL,
  `Q2` int(10) DEFAULT NULL,
  `Q3` int(10) DEFAULT NULL,
  `Q4` int(10) DEFAULT NULL,
  PRIMARY KEY (`U_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_qx`
--

LOCK TABLES `sys_user_qx` WRITE;
/*!40000 ALTER TABLE `sys_user_qx` DISABLE KEYS */;
INSERT INTO `sys_user_qx` VALUES ('1',1,0,0,0,0,0,0,0),('2',1,1,1,1,1,1,1,1),('30e123af109e4048ba9435285d87353b',0,0,0,0,0,0,0,0),('55896f5ce3c0494fa6850775a4e29ff6',0,0,0,0,0,0,0,0),('656f5e0bbdcd4caf8295e2c16450fec1',0,0,0,0,0,0,0,0),('68f23fc0caee475bae8d52244dea8444',0,0,0,0,0,0,0,0),('7061e633751d48f185726d07b2fd405d',0,0,0,0,0,0,0,0),('7dfd8d1f7b6245d283217b7e63eec9b2',0,0,0,0,0,0,0,0),('a39f690ec7984e60850e6b02115fa14c',0,0,0,0,0,0,0,0),('ac66961adaa2426da4470c72ffeec117',0,0,0,0,0,0,0,0),('b0c77c29dfa140dc9b14a29c056f824f',0,0,0,0,0,0,0,0),('ce7e1da376d645caa7b26116d9318066',0,0,0,0,0,0,0,0),('e74f713314154c35bd7fc98897859fe3',0,0,0,0,0,0,0,0),('f944a9df72634249bbcb8cb73b0c9b86',0,0,0,0,0,0,0,0),('fa7e8ab53c434f27a2c4ed5f5a5f4124',0,0,0,0,0,0,0,0);
/*!40000 ALTER TABLE `sys_user_qx` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-11 17:07:44
