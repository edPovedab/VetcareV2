-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: vetcaredb
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `cita`
--

DROP TABLE IF EXISTS `cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cita` (
  `id_cita` int NOT NULL AUTO_INCREMENT,
  `id_mascota` int NOT NULL,
  `id_propietario` int NOT NULL,
  `fecha_cita` datetime NOT NULL,
  `servicio` varchar(255) DEFAULT NULL,
  `notas` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_cita`),
  KEY `id_mascota` (`id_mascota`),
  CONSTRAINT `cita_ibfk_1` FOREIGN KEY (`id_mascota`) REFERENCES `mascota` (`id_mascota`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita`
--

LOCK TABLES `cita` WRITE;
/*!40000 ALTER TABLE `cita` DISABLE KEYS */;
INSERT INTO `cita` VALUES (35,1,1,'2025-04-01 10:30:00','Consulta General','Revisión anual de la mascota'),(38,9,0,'2025-04-05 22:41:00','conejo',NULL);
/*!40000 ALTER TABLE `cita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mascota`
--

DROP TABLE IF EXISTS `mascota`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mascota` (
  `id_mascota` int NOT NULL AUTO_INCREMENT,
  `id_propietario` int NOT NULL,
  `nombre_mascota` varchar(255) DEFAULT NULL,
  `especie` varchar(255) DEFAULT NULL,
  `raza` varchar(255) DEFAULT NULL,
  `edad` int DEFAULT NULL,
  PRIMARY KEY (`id_mascota`),
  KEY `id_propietario` (`id_propietario`),
  CONSTRAINT `mascota_ibfk_1` FOREIGN KEY (`id_propietario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mascota`
--

LOCK TABLES `mascota` WRITE;
/*!40000 ALTER TABLE `mascota` DISABLE KEYS */;
INSERT INTO `mascota` VALUES (1,1,'Firulais','Perro','Mestizo',3),(2,1,'julio','Perro',NULL,NULL),(3,1,'julio','Perro',NULL,NULL),(4,1,'julio','Perro',NULL,NULL),(5,1,'gss','DOBERMAN',NULL,NULL),(6,1,'nana','Gato',NULL,NULL),(7,1,'rocki2','perro',NULL,NULL),(8,1,'rocki2','perro',NULL,NULL),(9,1,'fifi','conejo',NULL,NULL);
/*!40000 ALTER TABLE `mascota` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id_producto` int NOT NULL AUTO_INCREMENT,
  `nombre_producto` varchar(255) NOT NULL,
  `descripcion` text,
  `precio` decimal(38,2) NOT NULL,
  `stock` int NOT NULL,
  `imagen_url` varchar(255) DEFAULT NULL,
  `categoria` varchar(255) DEFAULT NULL,
  `disponible` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'Meloxicam','Es un medicamento antiinflamatorio no esteroideo (AINE) que está aprobado para los gatos como una inyección única administrada antes de la cirugía para controlar el dolor y la inflamación después de esterilizaciones, castraciones y cirugías ortopédicas.',20.00,50,'/images/meloxicam.jpg','Medicamentos',1),(2,'NexGard','Es un medicamento para perros que sirve para tratar y prevenir infestaciones por pulgas, garrapatas, ácaros de la sarna y otros parásitos.',25.00,35,'/images/NexGard.jpg','Medicamentos',1),(3,'Naxpet','Es un medicamento veterinario que se usa para tratar el dolor, la inflamación y la fiebre en perros y gatos.',15.00,42,'/images/naxpet.jpg','Medicamentos',1),(4,'Correa para perros premium','Correa resistente y ajustable para perros de todos los tamaños. Material duradero y cómodo agarre.',18.50,20,NULL,'Accesorios',1),(5,'Comedero automático','Dispensador automático de alimentos con temporizador programable y compartimentos para varias comidas.',45.99,15,NULL,'Accesorios',1),(6,'Alimento premium para gatos','Alimento seco premium para gatos adultos. Rico en proteínas y nutrientes esenciales.',22.75,30,NULL,'Alimentos',1);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tratamiento`
--

DROP TABLE IF EXISTS `tratamiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tratamiento` (
  `id_tratamiento` int NOT NULL AUTO_INCREMENT,
  `id_mascota` int NOT NULL,
  `fecha_tratamiento` datetime NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`id_tratamiento`),
  KEY `id_mascota` (`id_mascota`),
  CONSTRAINT `tratamiento_ibfk_1` FOREIGN KEY (`id_mascota`) REFERENCES `mascota` (`id_mascota`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tratamiento`
--

LOCK TABLES `tratamiento` WRITE;
/*!40000 ALTER TABLE `tratamiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `tratamiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `rol` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `nombre_usuario` (`nombre_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'usuario_prueba','$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP.','USUARIO','usuario_prueba@example.com'),(2,'Gabriela Quirós','$2a$10$TL0FVrboZ3uREaLVfSHxu7BdJHZiV8KhYgYmFdVwfrNKof1chRa9C','USUARIO','Gabriela Quirós@example.com'),(3,'Marcela Campos','$2a$10$GgmkDh9s2nk/Jw9PLVh21.V5pUtV7lLzq.9/Zslpgyl5RPJHwMY9m','USUARIO','Marcela Campos@example.com'),(4,'María López','$2a$10$CGrPnve2pERmn71Mj3hZr6.oPEs7g6z1gpm2A2fhcW3poT2bCzD5u','USUARIO','María López@example.com'),(5,'Greivin Chinchilla','$2a$10$PlRl9Tchq27JrrWxIFkx9J3OEBmgvwdXAsdo9dZ5Q9ksYZhp9xPVe','USUARIO','Greivin Chinchilla@example.com'),(6,'admin','$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP.','ADMIN','admin@vetcare.com'),(7,'Eduardo','$2a$10$aNgEhXT7w9YyoDWUr2QKaOehe3hUVfAC5HSUb3lp6ByyM6XxUQRVG','USUARIO','edu89@gmail.com'),(8,'Administrador','$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP.','ADMIN','admin@vetcare.com'),(9,'Cheryl','$2a$10$DzEJ6/4LHg.n/AEmdjnsp.YAY.vXTfRJTG/lyzglYm3SxbMCzj6CG','USUARIO','cheryl123@gmail.com');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-31 22:48:26
