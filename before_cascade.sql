-- MySQL dump 10.13  Distrib 5.7.21, for osx10.12 (x86_64)
--
-- Host: localhost    Database: milestones
-- ------------------------------------------------------
-- Server version	5.7.21

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
-- Table structure for table `links`
--

DROP TABLE IF EXISTS `links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `links` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(65) NOT NULL DEFAULT '',
  `urlHash` varchar(20) NOT NULL DEFAULT '',
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dateLastAccessed` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `projectID` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `projectID` (`projectID`),
  CONSTRAINT `links_ibfk_1` FOREIGN KEY (`projectID`) REFERENCES `projects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `links`
--

LOCK TABLES `links` WRITE;
/*!40000 ALTER TABLE `links` DISABLE KEYS */;
INSERT INTO `links` VALUES (40,'chris.connor@spectrumdubai.com','51c42f9a','2018-04-27 21:48:50','2018-04-27 21:48:50',24),(41,'frankconnor@gmail.com','687e2bc6','2018-04-27 21:49:12','2018-04-27 21:49:12',5),(42,'chris@connor.com','a5ec29f4','2018-04-27 22:26:28','2018-04-27 22:26:28',33),(43,'khart@hartman.com','d09f2a51','2018-04-27 22:26:41','2018-04-27 22:26:41',33);
/*!40000 ALTER TABLE `links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `milestones`
--

DROP TABLE IF EXISTS `milestones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `milestones` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `description` text,
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dateModified` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `dueDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dateCompleted` timestamp NULL DEFAULT NULL,
  `projectID` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `projectID` (`projectID`),
  CONSTRAINT `milestones_ibfk_1` FOREIGN KEY (`projectID`) REFERENCES `projects` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `milestones`
--

LOCK TABLES `milestones` WRITE;
/*!40000 ALTER TABLE `milestones` DISABLE KEYS */;
INSERT INTO `milestones` VALUES (4,'Build servlets','Need to build some awesome servlets','2018-04-03 15:45:40','2018-04-26 23:22:12','2018-04-27 23:22:12',NULL,2),(5,'Build authorisation','Build the authorisation','2018-04-03 17:17:40','2018-04-26 23:22:12','2018-04-27 23:22:12',NULL,2),(6,'Build UI','Make sure the UI is built and looks good','2018-04-03 17:18:03','2018-04-26 23:22:12','2018-04-27 23:22:12',NULL,2),(7,'Brush up on C','It\'s been a while, I can\'t remember','2018-04-03 18:43:58','2018-04-26 23:22:12','2018-04-27 23:22:12',NULL,4),(8,'Look at pointers','I cannot remember pointers for the life of me...	','2018-04-03 18:44:20','2018-04-26 23:22:12','2018-04-27 23:22:12',NULL,4),(9,'Wireframes','Build some wireframes','2018-04-03 19:03:42','2018-04-26 23:22:12','2018-04-27 23:22:12',NULL,5),(10,'Mockups','Make some mockups in Adobe XD','2018-04-03 19:03:59','2018-04-26 23:22:12','2018-04-27 23:22:12',NULL,5),(11,'Initial DB Design','Do some db design ER diagrams in Visual Paradigm','2018-04-03 19:04:21','2018-04-26 23:22:12','2018-04-27 23:22:12',NULL,5),(16,'SHOULD GO ON 24','SHOULD GO ON 24','2018-04-27 15:57:48','2018-04-27 15:57:48','2018-04-25 20:57:00',NULL,24),(17,'This is due','What about this one?','2018-04-27 16:02:09','2018-04-27 16:02:09','2018-04-30 21:02:00',NULL,27),(19,'CASCADE','1','2018-04-27 22:26:03','2018-04-27 22:26:03','2018-04-28 03:26:00',NULL,33),(20,'CASCADE 2','TEST 2','2018-04-27 22:26:13','2018-04-27 22:26:13','2018-04-30 03:26:00',NULL,33);
/*!40000 ALTER TABLE `milestones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projects` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(65) NOT NULL DEFAULT '',
  `description` text,
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dateModified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userID` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userID` (`userID`),
  CONSTRAINT `projects_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (2,'Web Platform Development','This is a brilliant project...','2018-04-03 15:44:51','2018-04-03 15:44:51',1),(4,'Systems Programming','This is for C and the like - I think...','2018-04-03 18:43:27','2018-04-27 01:06:08',4),(5,'Web Design','Web Design Technologies Project','2018-04-03 19:03:21','2018-04-03 19:03:21',4),(6,'DUMMY FROM CLI','DUMMY FROM CLI','2018-04-24 16:10:24','2018-04-24 16:10:24',1),(24,'TEST UPDATE','TEST UPDATE THIS','2018-04-26 12:45:42','2018-04-26 14:48:15',28),(27,'Still works? Mehhhhs','Ok it should still work :)','2018-04-26 14:48:52','2018-04-26 15:15:59',28),(29,'Brave New Project','What do you think of this...should be cool, right?','2018-04-27 00:24:29','2018-04-27 00:24:29',4),(33,'CASCAE','CASCADE','2018-04-27 22:25:52','2018-04-27 22:25:52',28);
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `fname` varchar(100) NOT NULL DEFAULT '',
  `lname` varchar(100) NOT NULL DEFAULT '',
  `email` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `dateCreated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dateLoggedIn` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Chris','Connor','chris@chrisconnor.co.uk','1000:dec5af7e2ce212b169f4feaea10345dbaef29c8b9a04810d:7609218b6af5e3b827894ab800a8f10d3e9b8b94301d2939','2018-04-03 15:43:32',NULL),(2,'Daryl','McAllister','d.mcallister@domain.com','1000:dec5af7e2ce212b169f4feaea10345dbaef29c8b9a04810d:7609218b6af5e3b827894ab800a8f10d3e9b8b94301d2939','2018-04-03 15:43:55',NULL),(3,'Nik','Olsson','n.olsson@domain.com','1000:dec5af7e2ce212b169f4feaea10345dbaef29c8b9a04810d:7609218b6af5e3b827894ab800a8f10d3e9b8b94301d2939','2018-04-03 15:44:10',NULL),(4,'Gavin','MacLeod','g.macleod@domain.com','1000:dec5af7e2ce212b169f4feaea10345dbaef29c8b9a04810d:7609218b6af5e3b827894ab800a8f10d3e9b8b94301d2939','2018-04-03 15:44:27',NULL),(27,'Jon','Bon Jovi','jon@bonjovi.com','1000:3ef518cfc1d3290000dc47e48350b68c9357e10ae076b4b9:461df9094bee0d23850c91b0cc9a3d5ab718ccbf59e80a61','2018-04-25 06:23:57',NULL),(28,'Nik','Olsson','nik@olsson.com','1000:0dc434864e5cf18ee0ffb8cb9f9158e75c6a3ce0271ec6e4:55cda28f94b678d6e4a94ba38f553c133c23fc3275b5b424','2018-04-25 19:26:49',NULL),(29,'Michelle','Connor','michelleconnor227@gmail.com','1000:dec5af7e2ce212b169f4feaea10345dbaef29c8b9a04810d:7609218b6af5e3b827894ab800a8f10d3e9b8b94301d2939','2018-04-26 11:19:14',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-27 23:27:20
