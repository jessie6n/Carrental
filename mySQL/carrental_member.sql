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
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `member_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `id_number` varchar(255) DEFAULT NULL,
  `birthday` date NOT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `register_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`),
  UNIQUE KEY `member_id_UNIQUE` (`member_id`),
  UNIQUE KEY `id_number_UNIQUE` (`id_number`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'m00001','張小紫','0956123123','F225235235','1988-10-14','女','chang@abc.com','臺北市北投區宜山路17號','1234','2025-11-04 13:15:40'),(2,'m00002','吳小津','0958258569','F226586225','1988-10-03','女','kim@test.com.tw','台北市大安區安和路二段88號','chang','2025-11-05 00:10:58'),(5,'m00003','林小諾','0935856896','A134852582','1988-10-04','男','noahlin@test.com','台北市大安區敦化南路二段68號','chang','2025-11-18 22:16:42'),(11,'m00004','林咚咚','0928570123','A135852833','1986-01-25','男','oliverlin@test.com','台北市大安區敦化南路二段58號','chang','2025-11-18 23:55:48'),(12,'m00005','王小梅','0960785025','F225875202','1960-01-17','女','may@test.com','台北市文山區山林路50號','may123','2025-11-19 09:49:01'),(14,'m00006','游小魚','0930251569','F226856025','1987-06-12','女','fishyu@test.com','新北市板橋區文化路250號','fish123','2025-11-19 10:32:57'),(24,'m00007','林姍姍','0930251508','A228556332','1987-06-12','女','shanlin@test.com','新北市板橋區板新路520號','linlin','2025-11-19 12:57:23'),(26,'m00008','何小華','0987441256','F125998202','1978-03-31','男','hoho@test.com','高雄市苓雅區五福路350號','hohoho','2025-11-19 13:02:37'),(28,'m00009','宋可可','0985123582','A128523665','1987-04-12','男','sung@test.com','台北市中山區林森北路50號','sung','2025-11-19 17:03:07'),(29,'m00010','王小明','0912345678','A123456789','1990-01-01','男','test@example.com','台北市中正區重慶南路一段122號','123456789','2025-11-24 11:43:51'),(31,'m00011','AAA','0968413541','N124456698','2025-11-04','男','AAA@SSSS','','123456789','2025-11-24 15:38:16');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-26 13:13:39
