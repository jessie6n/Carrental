CREATE DATABASE  IF NOT EXISTS `carrental` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `carrental`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: carrental
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `subscription_order`
--

DROP TABLE IF EXISTS `subscription_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscription_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `member_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `car_id` int NOT NULL,
  `store` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `start_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `start_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `months` int DEFAULT NULL,
  `mileage_bonus` int DEFAULT NULL,
  `total_price` int DEFAULT NULL,
  `final_price` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription_order`
--

LOCK TABLES `subscription_order` WRITE;
/*!40000 ALTER TABLE `subscription_order` DISABLE KEYS */;
INSERT INTO `subscription_order` VALUES (1,'O202511240001','1',6,'台北車站','2025-11-29','15:24',9,200,125991,135991,'2025-11-24 14:22:34',NULL),(2,'O202511240002','1',6,'台北車站','2025-11-29','15:24',9,200,125991,135991,'2025-11-24 14:23:14',NULL),(3,'O202511240003','1',6,'台北車站','2025-11-29','15:24',9,200,125991,135991,'2025-11-24 14:24:15',NULL),(4,'O202511240004','1',3,'台北車站','','',3,0,32997,42997,'2025-11-24 14:29:07',NULL),(5,'O202511240005','1',3,'台北車站','2025-11-21','16:35',6,200,65994,75994,'2025-11-24 14:29:42',NULL),(6,'O202511240006','1',4,'台北車站','','',3,0,35997,45997,'2025-11-24 14:33:06',NULL),(7,'O202511240007','1',3,'台北車站','','',3,0,32997,42997,'2025-11-24 14:37:59',NULL),(8,'O202511240008','1',3,'台北車站','','',3,0,32997,42997,'2025-11-24 14:40:00',NULL),(9,'SUB202511271764229751524','m00007',4,'高雄車站','2025-11-29','19:00',3,0,35997,45997,'2025-11-27 07:49:11','進行中'),(10,'SUB202511281764301759615','m00010',2,'台北車站','2025-12-20','00:50',3,0,29997,39997,'2025-11-28 03:49:19','進行中');
/*!40000 ALTER TABLE `subscription_order` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-28 16:46:28
