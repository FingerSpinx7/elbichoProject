-- MariaDB dump 10.19  Distrib 10.4.28-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: elbicho
-- ------------------------------------------------------
-- Server version	10.4.28-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuenta` (
  `id_cuenta` int(10) NOT NULL,
  `id_mesa` int(10) NOT NULL,
  `fecha_inicio` datetime NOT NULL,
  `fecha_fin` datetime DEFAULT NULL,
  `Cliente` varchar(50) NOT NULL DEFAULT 'Cliente',
  PRIMARY KEY (`id_cuenta`),
  KEY `id_mesa` (`id_mesa`),
  CONSTRAINT `cuenta_ibfk_1` FOREIGN KEY (`id_mesa`) REFERENCES `mesa` (`id_mesa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES (1,1,'2024-01-03 21:48:55','2024-01-03 21:52:18','Luis'),(2,7,'2024-01-03 21:51:30','2024-01-03 23:55:44','David'),(3,2,'2024-01-03 23:34:59','2024-01-03 23:55:58','Mario'),(4,2,'2024-01-04 00:09:05','2024-01-04 00:09:24','Pepe'),(5,2,'2024-01-04 00:29:34','2024-01-04 00:30:19','Emiliano'),(6,2,'2024-01-04 00:39:12','2024-01-04 00:40:39','Fernando'),(7,7,'2024-01-04 00:40:59','2024-01-04 00:49:01','Miguel'),(8,2,'2024-01-04 00:47:56','2024-01-04 02:40:17','Elena'),(9,7,'2024-01-04 13:22:46','2024-01-04 15:35:25','Carlos'),(10,4,'2024-01-04 14:54:24','2024-01-04 15:03:57','Vania'),(11,1,'2024-01-04 15:47:24','2024-01-04 15:52:55','Bruno'),(12,4,'2024-01-04 16:27:35','2024-01-04 22:43:53','Chucho'),(13,2,'2024-01-04 23:24:35','2024-01-04 23:45:19','Emiliano'),(14,7,'2024-01-05 01:52:01','2024-01-05 02:40:46','Pablo'),(15,2,'2024-01-05 02:40:26','2024-01-05 02:44:00','Fulano'),(16,7,'2024-01-05 02:42:29','2024-01-05 22:41:29','Liliana'),(17,1,'2024-01-05 22:36:20','2024-01-06 23:53:59','leonardo'),(18,5,'2024-01-05 22:40:43','2024-01-06 23:54:15','erikita'),(19,2,'2024-01-07 00:37:04','2024-01-07 01:04:33','Flor'),(20,5,'2024-01-07 18:57:59','2024-01-07 19:03:52','jorge'),(21,5,'2024-01-07 22:34:52','2024-01-07 22:41:02','Diegoberto'),(22,3,'2024-01-08 08:13:59','2024-01-08 08:19:15','Isaac'),(23,1,'2024-01-08 08:17:12','2024-01-09 11:17:33','Juan'),(24,3,'2024-01-09 12:15:57','2024-01-09 12:32:25','Claudia');
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta_selected`
--

DROP TABLE IF EXISTS `cuenta_selected`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cuenta_selected` (
  `id_cuenta` int(11) NOT NULL,
  `num` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta_selected`
--

LOCK TABLES `cuenta_selected` WRITE;
/*!40000 ALTER TABLE `cuenta_selected` DISABLE KEYS */;
INSERT INTO `cuenta_selected` VALUES (4,1);
/*!40000 ALTER TABLE `cuenta_selected` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `datos_usuario`
--

DROP TABLE IF EXISTS `datos_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `datos_usuario` (
  `usuario` varchar(50) NOT NULL,
  `contraseña` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datos_usuario`
--

LOCK TABLES `datos_usuario` WRITE;
/*!40000 ALTER TABLE `datos_usuario` DISABLE KEYS */;
INSERT INTO `datos_usuario` VALUES ('admin','admin');
/*!40000 ALTER TABLE `datos_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesa`
--

DROP TABLE IF EXISTS `mesa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mesa` (
  `id_mesa` int(10) NOT NULL,
  `tipo_mesa` varchar(50) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id_mesa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesa`
--

LOCK TABLES `mesa` WRITE;
/*!40000 ALTER TABLE `mesa` DISABLE KEYS */;
INSERT INTO `mesa` VALUES (1,'POOL',0),(2,'CARAMBOLA',0),(3,'POOL',0),(4,'POOL',0),(5,'CARAMBOLA',0),(6,'CARAMBOLA',0),(7,'POOL',0),(8,'POOL',0),(9,'POOL',0),(10,'POOL',0),(11,'POOL',0),(12,'CARAMBOLA',0);
/*!40000 ALTER TABLE `mesa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesa_actual`
--

DROP TABLE IF EXISTS `mesa_actual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mesa_actual` (
  `num` int(11) NOT NULL,
  `mesa_act_col` int(11) NOT NULL,
  KEY `mesa_act_col` (`mesa_act_col`),
  CONSTRAINT `mesa_actual_ibfk_1` FOREIGN KEY (`mesa_act_col`) REFERENCES `mesa` (`id_mesa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesa_actual`
--

LOCK TABLES `mesa_actual` WRITE;
/*!40000 ALTER TABLE `mesa_actual` DISABLE KEYS */;
INSERT INTO `mesa_actual` VALUES (1,3);
/*!40000 ALTER TABLE `mesa_actual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productos` (
  `id_producto` int(10) NOT NULL,
  `id_proveedor` int(10) NOT NULL,
  `det_producto` varchar(50) NOT NULL,
  `precio` int(10) NOT NULL,
  `disponibilidad` int(11) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id_producto`),
  KEY `id_proveedor` (`id_proveedor`),
  CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id_proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,1,'Papas adobadas',20,1),(2,1,'Papas con limon',20,1),(3,1,'Coca Light',18,0),(4,1,'maruchan',20,1),(5,1,'sidral3',34,0),(7,2,'Totis con chile',19,1),(8,6,'Panditas',7,0),(9,3,'Pelones',10,0),(10,2,'panditas',7,0),(11,3,'panditas',8,1),(12,5,'Platanos secos',12,1),(13,4,'takis',13,0),(14,7,'fanta de 3 litros',45,1),(15,3,'papas sol clasicas',13,1),(16,3,'Corona',25,1),(17,3,'Top - tops morados',12,1);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos_consumidos`
--

DROP TABLE IF EXISTS `productos_consumidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productos_consumidos` (
  `id_cuenta` int(11) NOT NULL,
  `id_producto` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  KEY `id_cuenta` (`id_cuenta`,`id_producto`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `productos_consumidos_ibfk_1` FOREIGN KEY (`id_cuenta`) REFERENCES `cuenta` (`id_cuenta`),
  CONSTRAINT `productos_consumidos_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos_consumidos`
--

LOCK TABLES `productos_consumidos` WRITE;
/*!40000 ALTER TABLE `productos_consumidos` DISABLE KEYS */;
INSERT INTO `productos_consumidos` VALUES (1,3,3),(1,5,1),(2,4,1),(4,2,2),(5,2,1),(5,4,2),(5,5,1),(8,2,2),(9,9,3),(10,4,2),(10,2,1),(11,2,1),(11,10,1),(12,2,2),(12,3,3),(14,2,2),(16,4,3),(15,1,1),(17,1,4),(20,4,1),(21,4,2),(22,16,4),(22,4,2),(24,4,3);
/*!40000 ALTER TABLE `productos_consumidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proveedor` (
  `id_proveedor` int(10) NOT NULL,
  `telefono` bigint(15) NOT NULL,
  `nombre` varchar(60) NOT NULL,
  PRIMARY KEY (`id_proveedor`),
  UNIQUE KEY `telefono_3` (`telefono`),
  KEY `telefono` (`telefono`),
  KEY `telefono_2` (`telefono`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (1,5528523863,'Veronica'),(2,5514963574,'Saul'),(3,5547896345,'Leonardo'),(4,7745962385,'Samuel'),(5,5548963756,'Carlos'),(6,8875693421,'Erika'),(7,1234567890,'Lalo maruchans'),(8,5510965444,'emilio pelones'),(9,1234567895,'Gloria');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-09 12:41:12
