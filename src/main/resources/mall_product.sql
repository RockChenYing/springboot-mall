CREATE DATABASE  IF NOT EXISTS `mall` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mall`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: mall
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(128) NOT NULL,
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image_url` varchar(256) NOT NULL,
  `price` int NOT NULL,
  `stock` int NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `created_date` timestamp NOT NULL,
  `last_modified_date` timestamp NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (5,'蘋果（澳洲）','FOOD','https://cdn.pixabay.com/photo/2016/11/30/15/00/apples-1872997_1280.jpg',30,99,'這是來自澳洲的蘋果唷！','2022-03-19 09:00:00','2025-01-19 19:43:22'),(6,'蘋果（日本北海道）','FOOD','https://cdn.pixabay.com/photo/2017/09/26/13/42/apple-2788662_1280.jpg',300,50,'這是來自日本北海道的蘋果！','2022-03-19 10:30:00','2025-01-19 19:56:00'),(7,'好吃又鮮甜的蘋果橘子','FOOD','https://cdn.pixabay.com/photo/2021/07/30/04/17/orange-6508617_1280.jpg',10,41,'年末大拍賣','2022-03-20 01:00:00','2025-01-16 01:03:01'),(8,'Toyota','CAR','https://cdn.pixabay.com/photo/2014/05/18/19/13/toyota-347288_1280.jpg',100000,4,NULL,'2022-03-20 01:20:00','2022-03-20 01:20:00'),(9,'BMW','CAR','https://cdn.pixabay.com/photo/2018/02/21/03/15/bmw-m4-3169357_1280.jpg',500000,10,'渦輪增壓，直列4缸，DOHC雙凸輪軸，16氣門','2022-03-20 04:30:00','2025-01-19 19:40:04'),(10,'Benz','CAR','https://cdn.pixabay.com/photo/2017/03/27/14/56/auto-2179220_1280.jpg',600000,69,'','2022-03-21 12:10:00','2025-01-17 00:38:03'),(19,'古典書籍','E_BOOK','https://cdn.pixabay.com/photo/2015/11/19/21/10/glasses-1052010_1280.jpg',500,16,'','2025-01-16 00:42:31','2025-01-16 00:42:31'),(21,'Hunter x Hunter','E_BOOK','https://img.feebee.tw/i/RX7QMo07_O8XQ4eJ4aEnaJmyeVvTJg-2--QRqX92l10/372/aHR0cHM6Ly9jZi5zaG9wZWUudHcvZmlsZS9zZy0xMTEzNDIwMS03cmVuNS1tMXkwcHEzaGJ5aW4wNw.webp',999999,9,'貪婪之島','2025-01-19 19:25:09','2025-01-21 19:26:29');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-22 14:33:40
