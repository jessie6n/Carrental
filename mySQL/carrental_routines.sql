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
-- Temporary view structure for view `ordermember_view`
--

DROP TABLE IF EXISTS `ordermember_view`;
/*!50001 DROP VIEW IF EXISTS `ordermember_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `ordermember_view` AS SELECT 
 1 AS `id`,
 1 AS `order_no`,
 1 AS `member_no`,
 1 AS `pickup_location`,
 1 AS `return_location`,
 1 AS `pickup_date`,
 1 AS `return_date`,
 1 AS `pickup_time`,
 1 AS `return_time`,
 1 AS `car_id`,
 1 AS `car_name`,
 1 AS `car_price`,
 1 AS `rental_days`,
 1 AS `insurance`,
 1 AS `child_seat_qty`,
 1 AS `total_amount`,
 1 AS `status`,
 1 AS `created_at`,
 1 AS `member_idnumber`,
 1 AS `member_name`,
 1 AS `member_phone`,
 1 AS `member_email`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `ordermember_view`
--

/*!50001 DROP VIEW IF EXISTS `ordermember_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `ordermember_view` AS select `o`.`id` AS `id`,`o`.`order_no` AS `order_no`,`o`.`member_no` AS `member_no`,`o`.`pickup_location` AS `pickup_location`,`o`.`return_location` AS `return_location`,`o`.`pickup_date` AS `pickup_date`,`o`.`return_date` AS `return_date`,`o`.`pickup_time` AS `pickup_time`,`o`.`return_time` AS `return_time`,`o`.`car_id` AS `car_id`,`o`.`car_name` AS `car_name`,`o`.`car_price` AS `car_price`,`o`.`rental_days` AS `rental_days`,cast(`o`.`insurance` as signed) AS `insurance`,`o`.`child_seat_qty` AS `child_seat_qty`,`o`.`total_amount` AS `total_amount`,`o`.`status` AS `status`,`o`.`created_at` AS `created_at`,`m`.`id_number` AS `member_idnumber`,`m`.`name` AS `member_name`,`m`.`phone` AS `member_phone`,`m`.`email` AS `member_email` from (`orders` `o` join `member` `m` on((`m`.`member_id` = `o`.`member_no`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-26 13:13:39
