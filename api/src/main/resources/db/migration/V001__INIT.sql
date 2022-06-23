-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: localhost    Database: tqs_private_41
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `zipcode` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (7,'Rua 25 de Abril, nº704','Ovar','Portugal','3880-345'),(8,'Rua José de Castro, nº9','Gaia','Portugal','4400-642'),(9,'Avenida São Nicolau, 2ªandar, 2ºesq','Coimbra','Portugal','3000-923'),(10,'Rua do Canatro, nº34','Aveiro','Portugal','3800-764'),(11,'Rua Afonso Bernardes, nº87','Porto','Portugal','4000-173'),(12,'Rua Infante Dão Henrique, nº234','Coimbra','Portugal','3000-357');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_list`
--

DROP TABLE IF EXISTS `cart_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_list` (
  `product_list_id` bigint NOT NULL,
  PRIMARY KEY (`product_list_id`),
  CONSTRAINT `FKe7qflhurks906apibejkinfn5` FOREIGN KEY (`product_list_id`) REFERENCES `product_list` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_list`
--

LOCK TABLES `cart_list` WRITE;
/*!40000 ALTER TABLE `cart_list` DISABLE KEYS */;
INSERT INTO `cart_list` VALUES (3),(11);
/*!40000 ALTER TABLE `cart_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,_binary '','Peixaria'),(2,_binary '','Charcutaria | Talho'),(3,_binary '','Frutas e Legumes'),(4,_binary '','Lácteos'),(5,_binary '','Congelados'),(6,_binary '','Alimentação'),(7,_binary '','Gordices'),(8,_binary '','Bebidas'),(9,_binary '','Higiene Pessoal');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_list`
--

DROP TABLE IF EXISTS `order_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_list` (
  `product_list_id` bigint NOT NULL,
  `delivery_id` bigint NOT NULL,
  `delivery_timestamp` datetime(6) DEFAULT NULL,
  `order_timestamp` datetime(6) DEFAULT NULL,
  `address_id` bigint NOT NULL,
  `store_id` bigint NOT NULL,
  PRIMARY KEY (`product_list_id`),
  KEY `FKpiheai9ganrgus5q6brw6cjkp` (`address_id`),
  KEY `FKhc5vidtreel1lulnwpgppk7u3` (`store_id`),
  CONSTRAINT `FK1wtaojpdeu76crim0s7kujhce` FOREIGN KEY (`product_list_id`) REFERENCES `product_list` (`id`),
  CONSTRAINT `FKhc5vidtreel1lulnwpgppk7u3` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`),
  CONSTRAINT `FKpiheai9ganrgus5q6brw6cjkp` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_list`
--

LOCK TABLES `order_list` WRITE;
/*!40000 ALTER TABLE `order_list` DISABLE KEYS */;
INSERT INTO `order_list` VALUES (3,0,'2022-06-21 09:54:00.000000','2022-06-21 09:54:45.710768',13,2);
/*!40000 ALTER TABLE `order_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_product_item`
--

DROP TABLE IF EXISTS `order_product_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_product_item` (
  `list_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`list_id`,`product_id`),
  KEY `FK8n0wlqutvt5bhk9v2pd8ay04s` (`product_id`),
  CONSTRAINT `FK8n0wlqutvt5bhk9v2pd8ay04s` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKnd2n6qfo6k00xbpmkuf953vly` FOREIGN KEY (`list_id`) REFERENCES `order_list` (`product_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_product_item`
--

LOCK TABLES `order_product_item` WRITE;
/*!40000 ALTER TABLE `order_product_item` DISABLE KEYS */;
INSERT INTO `order_product_item` VALUES (3,57,1.1200000047683716);
/*!40000 ALTER TABLE `order_product_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (53,'COVIRAN (700g)',_binary '','Dourada Média Fresca',4.99,1),(54,'COVIRAN (400g)',_binary '','Robalo Médio Fresco',3.59,1),(55,'COVIRAN (2.5kg)',_binary '','Bacalhau Graúdo 1ª Noruega Seco',9.79,1),(56,'SICASAL (100g)',_binary '','Fiambre perna extra',1.75,2),(57,'CAMPOFRIO (11,20€/kg)',_binary '','Fiambre Peito de Perú',1.12,2),(58,'Talhos Rosa - emb. 4 uni (480 gr)',_binary '','Hambúrguer de Bovino',4.69,2),(59,'Origem Costa Rica/Colômbia',_binary '','Abacaxi Gold',1.19,3),(60,'Origem Equador',_binary '','Banana importada',1.09,3),(61,'Origem Australiana, rica em vitaminas',_binary '','Laranja Lane Late',0.95,3),(62,'Origem Nacional',_binary '','Cenouras',0.55,3),(63,'Origem Nacional',_binary '','Cebola Nova',0.89,3),(64,'Origem Nacional',_binary '','Tomate Cherry',0.99,3),(65,'YOGGI s/lactose (160g x 4uni.) ',_binary '','Iogurte Líquido Morango',1.99,4),(66,'TERRA NOSTRA (200g)',_binary '','Queijo fatias original',2.3,4),(67,'Mimosa (1L)',_binary '','Leite Meio Grosso',0.77,4),(68,'PESCANOVA (250g)',_binary '','Lombos Salmão',5.98,5),(69,'DR. OETKER (355g)',_binary '','Pizzas RISTORANTE',3.19,5),(70,'CARTE D\'OR (1,7L)',_binary '','Gelado Baunilha',2.79,5),(71,'MILANEZA (500g)',_binary '','Massa Linguini',1.08,6),(72,'NOBRE (8 uni.)',_binary '','Salsichas de Frango',1.23,6),(73,'GALLO (1L)',_binary '','Azeite Subtil',2.39,6),(74,'CIGALA (1kg)',_binary '','Arroz Basmati',1.59,6),(75,'BOM PETISCO (120g)',_binary '','Atum natural',0.99,6),(76,'MILKA (100g)',_binary '','Chocolate Oreo',1.39,7),(77,'FASTIO (1,5L)',_binary '','Água s/gás',0.53,8),(78,'SUMOL (1,5L)',_binary '','Refrigerante Laranja',1.19,8),(79,'SUPER BOCK (33cl x 6 uni.)',_binary '','Cerveja branca',3.99,8),(80,'FAIRY (540ml)',_binary '','Detergente Louça',2.09,9),(81,'SKIP pó (46 doses)',_binary '','Detergente Roupa',9.99,9),(82,'RENOVA extra XXL (1 rolo)',_binary '','Rolo Cozinha',1.99,9);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_list`
--

DROP TABLE IF EXISTS `product_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_list` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnd7d9oyoqinx7yxajtyn6dgu4` (`user_id`),
  CONSTRAINT `FKnd7d9oyoqinx7yxajtyn6dgu4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_list`
--

LOCK TABLES `product_list` WRITE;
/*!40000 ALTER TABLE `product_list` DISABLE KEYS */;
INSERT INTO `product_list` VALUES (1,49),(2,49),(3,49),(4,49),(5,49),(6,49),(7,49),(8,49),(9,49),(10,49),(11,49);
/*!40000 ALTER TABLE `product_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_list_item`
--

DROP TABLE IF EXISTS `product_list_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_list_item` (
  `list_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `amount` int NOT NULL,
  PRIMARY KEY (`list_id`,`product_id`),
  KEY `FKisskbd0nhh30voqjd4wfjv7p8` (`product_id`),
  CONSTRAINT `FKakryhxjaw6ut2dhvctl4djvm3` FOREIGN KEY (`list_id`) REFERENCES `product_list` (`id`),
  CONSTRAINT `FKisskbd0nhh30voqjd4wfjv7p8` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_list_item`
--

LOCK TABLES `product_list_item` WRITE;
/*!40000 ALTER TABLE `product_list_item` DISABLE KEYS */;
INSERT INTO `product_list_item` VALUES (1,53,1),(1,61,4),(3,57,1),(7,53,2),(11,53,1),(11,61,4);
/*!40000 ALTER TABLE `product_list_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saved_list`
--

DROP TABLE IF EXISTS `saved_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saved_list` (
  `product_list_id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`product_list_id`),
  CONSTRAINT `FKje2o35m2qm4kro0xxwyi23axh` FOREIGN KEY (`product_list_id`) REFERENCES `product_list` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saved_list`
--

LOCK TABLES `saved_list` WRITE;
/*!40000 ALTER TABLE `saved_list` DISABLE KEYS */;
INSERT INTO `saved_list` VALUES (1,'pylance'),(7,'serras');
/*!40000 ALTER TABLE `saved_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `address_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp2sen6ouwnlht537csk0kip90` (`address_id`),
  CONSTRAINT `FKp2sen6ouwnlht537csk0kip90` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES (1,'COVIRAN PORTO',11),(2,'COVIRAN COIMBRA',12),(3,'COVIRAN AVEIRO',10);
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `birthday` date DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `is_admin` bit(1) NOT NULL,
  `is_staff` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (46,'1948-01-08','lucilia@gmail.com',_binary '\0',_binary '\0','Lucília Santos','santos','912 345 642'),(47,'1983-05-27','andre@gmail.com',_binary '',_binary '\0','André Vaz','vaz','962 531 971'),(48,'2003-12-04','duarte@gmail.com',_binary '\0',_binary '','Duarte Henriques','henriques','918 496 114'),(49,'2022-06-06','admin@admin.pt',_binary '\0',_binary '\0','admin','admin','987659865432');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_address`
--

DROP TABLE IF EXISTS `user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_address` (
  `address_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`address_id`),
  KEY `FKk2ox3w9jm7yd6v1m5f68xibry` (`user_id`),
  CONSTRAINT `FKdaaxogn1ss81gkcsdn05wi6jp` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FKk2ox3w9jm7yd6v1m5f68xibry` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_address`
--

LOCK TABLES `user_address` WRITE;
/*!40000 ALTER TABLE `user_address` DISABLE KEYS */;
INSERT INTO `user_address` VALUES (13,49),(14,49),(15,49),(16,49);
/*!40000 ALTER TABLE `user_address` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-21 15:31:51
