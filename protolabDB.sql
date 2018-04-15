-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: protolab_db
-- ------------------------------------------------------
-- Server version	5.7.21-log

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
-- Table structure for table `dane_logowania`
--

DROP TABLE IF EXISTS `dane_logowania`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dane_logowania` (
  `ID_konta` int(11) NOT NULL AUTO_INCREMENT,
  `Login` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `Haslo` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`ID_konta`),
  UNIQUE KEY `Haslo_UNIQUE` (`Haslo`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dane_logowania`
--

LOCK TABLES `dane_logowania` WRITE;
/*!40000 ALTER TABLE `dane_logowania` DISABLE KEYS */;
INSERT INTO `dane_logowania` VALUES (1,'admin','admin'),(2,'user1','aaa'),(3,'user2','bbb'),(4,'user3','ccc'),(5,'user4','ddd'),(6,'user5','eee'),(7,'user6','fff'),(8,'user7','ggg'),(9,'user8','hhh'),(10,'user9','iii'),(11,'user10','jjj'),(12,'user11','kkk'),(13,'user12','lll'),(14,'user13','mmm'),(15,'user14','nnn');
/*!40000 ALTER TABLE `dane_logowania` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `przedmioty`
--

DROP TABLE IF EXISTS `przedmioty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `przedmioty` (
  `ID_przedmiotu` int(11) NOT NULL AUTO_INCREMENT,
  `Nazwa` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `Rodzaj` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  `Ilosc` int(11) NOT NULL,
  `Status` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`ID_przedmiotu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `przedmioty`
--

LOCK TABLES `przedmioty` WRITE;
/*!40000 ALTER TABLE `przedmioty` DISABLE KEYS */;
/*!40000 ALTER TABLE `przedmioty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rezerwacje`
--

DROP TABLE IF EXISTS `rezerwacje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rezerwacje` (
  `idRezerwacji` int(11) NOT NULL AUTO_INCREMENT,
  `ID_uzytkownika` int(11) NOT NULL,
  `ID_przedmiotu` int(11) NOT NULL,
  `od_kiedy` date NOT NULL,
  `do_kiedy` date NOT NULL,
  `nazwaPrzedmiotu` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `ilosc` int(11) NOT NULL,
  PRIMARY KEY (`idRezerwacji`),
  UNIQUE KEY `idRezerwacji_UNIQUE` (`idRezerwacji`),
  KEY `ID_uzytkownika_idx` (`ID_uzytkownika`),
  KEY `ID_przedmiotu_idx` (`ID_przedmiotu`),
  CONSTRAINT `ID_przedmiotu` FOREIGN KEY (`ID_przedmiotu`) REFERENCES `przedmioty` (`ID_przedmiotu`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ID_uzytkownika` FOREIGN KEY (`ID_uzytkownika`) REFERENCES `uzytkownicy` (`ID_uzytkownika`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezerwacje`
--

LOCK TABLES `rezerwacje` WRITE;
/*!40000 ALTER TABLE `rezerwacje` DISABLE KEYS */;
/*!40000 ALTER TABLE `rezerwacje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uprawnienia`
--

DROP TABLE IF EXISTS `uprawnienia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uprawnienia` (
  `ID_uprawnienia` int(11) NOT NULL,
  `rodzajUprawnienia` varchar(45) COLLATE utf8mb4_polish_ci NOT NULL,
  PRIMARY KEY (`ID_uprawnienia`),
  UNIQUE KEY `rodzajUprawnienia_UNIQUE` (`rodzajUprawnienia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uprawnienia`
--

LOCK TABLES `uprawnienia` WRITE;
/*!40000 ALTER TABLE `uprawnienia` DISABLE KEYS */;
/*!40000 ALTER TABLE `uprawnienia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uzytkownicy`
--

DROP TABLE IF EXISTS `uzytkownicy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uzytkownicy` (
  `ID_uzytkownika` int(11) NOT NULL AUTO_INCREMENT,
  `ID_uprawnienia` int(11) NOT NULL,
  `imie` varchar(45) COLLATE utf8mb4_polish_ci NOT NULL,
  `nazwisko` varchar(45) COLLATE utf8mb4_polish_ci NOT NULL,
  `numerTel` int(11) NOT NULL,
  `email` varchar(45) COLLATE utf8mb4_polish_ci NOT NULL,
  `pesel` int(11) NOT NULL,
  PRIMARY KEY (`ID_uzytkownika`),
  UNIQUE KEY `pesel_UNIQUE` (`pesel`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `numerTel_UNIQUE` (`numerTel`),
  UNIQUE KEY `ID_uprawnienia_UNIQUE` (`ID_uprawnienia`),
  UNIQUE KEY `ID_uzytkownika_UNIQUE` (`ID_uzytkownika`),
  CONSTRAINT `ID_uprawnienia` FOREIGN KEY (`ID_uprawnienia`) REFERENCES `uprawnienia` (`ID_uprawnienia`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uzytkownicy`
--

LOCK TABLES `uzytkownicy` WRITE;
/*!40000 ALTER TABLE `uzytkownicy` DISABLE KEYS */;
/*!40000 ALTER TABLE `uzytkownicy` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-15 15:48:40
