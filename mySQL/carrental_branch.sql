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
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '據點代號',
  `branch_id` varchar(255) DEFAULT NULL,
  `name_zh` varchar(255) DEFAULT NULL,
  `name_en` varchar(255) DEFAULT NULL,
  `region` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `address` varchar(255) NOT NULL COMMENT '完整地址 (用於地圖連結生成)',
  `phone_number` varchar(255) DEFAULT NULL,
  `opening_hours` varchar(255) DEFAULT NULL,
  `map_thumbnail_url` varchar(255) DEFAULT NULL COMMENT '地圖縮圖檔案路徑',
  PRIMARY KEY (`id`),
  UNIQUE KEY `branch_id_UNIQUE` (`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES (1,'taipei','台北車站店','Taipei Station Branch','北部','臺北市','臺北市大同區重慶北路一段26巷19號','(02) 2765-8888','08:00 - 22:00','./imgs/map-taipei.jpg'),(2,'taoyuan','桃園機場店','Taoyuan Airport Branch','北部','桃園市','桃園市大園區航站北路 9 號 (第一航廈)','(03) 393-0123','24 小時營業','./imgs/map-taoyuan.jpg'),(3,'hsinchu','新竹高鐵店','Hsinchu HSR Branch','北部','新竹縣','新竹縣竹北市高鐵七路 30 號 (高鐵站旁)','(03) 657-3333','09:00 - 21:00','./imgs/map-hsinchu.jpg'),(4,'taichung_hsr','台中高鐵店','Taichung HSR Station','中部','台中市','台中市烏日區站區二路8號 (高鐵站內)','(04) 3600-1234','07:00 - 23:00','./imgs/map-taichung-hsr.jpg'),(5,'taichung_station','台中火車站店','Taichung Railway Station','中部','台中市','台中市中區台灣大道一段1號','(04) 2222-5678','08:30 - 21:30','./imgs/map-taichung-train.jpg'),(6,'tainan_station','台南火車站店','Tainan Station','南部','台南市','台南市東區北門路二段4號','(06) 211-8888','09:00 - 21:00','./imgs/map-tainan.jpg'),(7,'kaohsiung_zuoying','高雄左營高鐵店','Kaohsiung Zuoying HSR','南部','高雄市','高雄市左營區高鐵路105號','(07) 960-9999','08:00 - 22:00','./imgs/map-kaohsiung.jpg'),(8,'hualien_station','花蓮站前店','Hualien Station','東部','花蓮縣','花蓮縣花蓮市國聯一路100號','(03) 833-6666','08:30 - 20:30','./imgs/map-hualien.jpg'),(9,'taitung_station','台東火車站店','Taitung Station','東部','台東縣','台東縣台東市岩灣路101巷598號','(089) 222-333','09:00 - 20:00','./imgs/map-taitung.jpg');
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
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
