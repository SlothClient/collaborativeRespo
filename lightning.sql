-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: lightning
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `approval`
--

DROP TABLE IF EXISTS `approval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `approval` (
  `id` int NOT NULL AUTO_INCREMENT,
  `planId` int NOT NULL,
  `applicantId` int NOT NULL,
  `aplyTime` date NOT NULL,
  `approvalTime` date NOT NULL,
  `approverId` int NOT NULL,
  `state` varchar(20) NOT NULL,
  `fatherId` int DEFAULT NULL,
  `level` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `approval_id_uindex` (`id`),
  KEY `approval_maintenance_id_fk` (`planId`),
  KEY `approval_staff_id_fk` (`applicantId`),
  KEY `approval_staff_id_fk_2` (`approverId`),
  CONSTRAINT `approval_maintenance_id_fk` FOREIGN KEY (`planId`) REFERENCES `maintenance` (`id`),
  CONSTRAINT `approval_staff_id_fk` FOREIGN KEY (`applicantId`) REFERENCES `staff` (`id`),
  CONSTRAINT `approval_staff_id_fk_2` FOREIGN KEY (`approverId`) REFERENCES `staff` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `approval`
--

LOCK TABLES `approval` WRITE;
/*!40000 ALTER TABLE `approval` DISABLE KEYS */;
INSERT INTO `approval` VALUES (1,1,2,'2024-01-01','2024-01-01',1,'已批准',NULL,1),(2,2,2,'2024-01-27','2024-01-31',1,'已批准',NULL,1),(3,3,4,'2024-02-27','2024-03-01',3,'已批准',NULL,1),(4,4,4,'2024-03-26','2024-03-30',3,'已批准',NULL,1),(5,5,6,'2024-04-27','2024-04-29',6,'已批准',NULL,1),(6,6,6,'2024-05-25','2024-05-31',6,'已批准',NULL,1),(7,7,4,'2024-06-22','2024-06-30',3,'已批准',NULL,1);
/*!40000 ALTER TABLE `approval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `check_items`
--

DROP TABLE IF EXISTS `check_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `checkId` int NOT NULL,
  `name` varchar(255) NOT NULL,
  `result` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `check_items_id_uindex` (`id`),
  KEY `check_items_checks_id_fk` (`checkId`),
  CONSTRAINT `check_items_checks_id_fk` FOREIGN KEY (`checkId`) REFERENCES `checks` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_items`
--

LOCK TABLES `check_items` WRITE;
/*!40000 ALTER TABLE `check_items` DISABLE KEYS */;
INSERT INTO `check_items` VALUES (1,1,'检查项1','合格'),(2,1,'检查项2','合格'),(3,3,'检查项1','合格'),(4,4,'检查项1','合格'),(5,5,'检查项1','合格'),(6,6,'检查项1','合格');
/*!40000 ALTER TABLE `check_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checks`
--

DROP TABLE IF EXISTS `checks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `deviceId` int NOT NULL,
  `time` date NOT NULL,
  `workId` int NOT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `result` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `checks_id_uindex` (`id`),
  KEY `checks_device_id_fk` (`deviceId`),
  KEY `checks_staff_id_fk` (`workId`),
  CONSTRAINT `checks_device_id_fk` FOREIGN KEY (`deviceId`) REFERENCES `device` (`id`),
  CONSTRAINT `checks_staff_id_fk` FOREIGN KEY (`workId`) REFERENCES `staff` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checks`
--

LOCK TABLES `checks` WRITE;
/*!40000 ALTER TABLE `checks` DISABLE KEYS */;
INSERT INTO `checks` VALUES (1,1,'2024-01-01',1,'无','合格'),(2,2,'2024-02-01',2,'无','合格'),(3,3,'2024-03-01',3,'无','合格'),(4,4,'2024-04-01',4,'无','合格'),(5,5,'2024-05-01',5,'无','合格'),(6,6,'2024-06-01',6,'无','合格');
/*!40000 ALTER TABLE `checks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fatherId` int DEFAULT NULL,
  `level` varchar(255) NOT NULL DEFAULT '0',
  `departmentName` varchar(255) NOT NULL,
  `administratorId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `department_id_uindex` (`id`),
  KEY `department_staff_id_fk` (`administratorId`),
  CONSTRAINT `department_staff_id_fk` FOREIGN KEY (`administratorId`) REFERENCES `staff` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,NULL,'1','总部',1),(2,1,'2','技术部',3),(3,1,'2','市场部',5);
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bigRepairsNumber` int DEFAULT NULL,
  `middleRepairsNumber` int DEFAULT NULL,
  `smallRepairsNumber` int DEFAULT NULL,
  `bigRepairsCycle` int DEFAULT NULL,
  `middleRepairsCycle` int DEFAULT NULL,
  `smallRepairsCycle` int DEFAULT NULL,
  `lastMaintenanceTime` date DEFAULT NULL,
  `validPeriod` int DEFAULT NULL,
  `deviceName` varchar(50) NOT NULL,
  `status` varchar(20) DEFAULT NULL,
  `notes` varchar(100) DEFAULT NULL,
  `supplierId` int DEFAULT NULL,
  `departmentId` int DEFAULT NULL,
  `typeId` int DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `purchasingDate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `device_id_uindex` (`id`),
  KEY `device_department_id_fk` (`departmentId`),
  KEY `device_supplier_id_fk` (`supplierId`),
  KEY `device_type_id_fk` (`typeId`),
  CONSTRAINT `device_department_id_fk` FOREIGN KEY (`departmentId`) REFERENCES `department` (`id`),
  CONSTRAINT `device_supplier_id_fk` FOREIGN KEY (`supplierId`) REFERENCES `supplier` (`id`),
  CONSTRAINT `device_type_id_fk` FOREIGN KEY (`typeId`) REFERENCES `type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES (1,0,1,1,30,15,5,'2024-02-07',365,'设备1','正常','无',2,1,1,'位置1','photo1.jpg','2023-09-01'),(2,1,0,0,45,15,5,'2024-08-17',365,'设备2','正常','无',2,1,2,'位置2','photo2.jpg','2023-10-01'),(3,0,0,1,30,15,5,'2024-03-04',365,'设备3','正常','无',3,2,3,'位置3','photo3.jpg','2023-09-01'),(4,0,0,1,25,10,3,'2024-04-02',365,'设备4','正常','无',4,2,4,'位置4','photo4.jpg','2023-10-01'),(5,0,1,0,20,10,5,'2024-05-09',365,'设备5','正常','无',5,3,5,'位置5','photo5.jpg','2023-09-07'),(6,0,1,0,30,15,5,'2024-06-17',365,'设备6','正常','无',6,3,6,'位置6','photo6.jpg','2023-10-07');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_maintenance`
--

DROP TABLE IF EXISTS `device_maintenance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device_maintenance` (
  `deviceId` int DEFAULT NULL,
  `maintenanceId` int DEFAULT NULL,
  UNIQUE KEY `device_maintenance_pk` (`deviceId`,`maintenanceId`),
  KEY `device_maintenance_maintenance_id_fk` (`maintenanceId`),
  CONSTRAINT `device_maintenance_device_id_fk` FOREIGN KEY (`deviceId`) REFERENCES `device` (`id`),
  CONSTRAINT `device_maintenance_maintenance_id_fk` FOREIGN KEY (`maintenanceId`) REFERENCES `maintenance` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_maintenance`
--

LOCK TABLES `device_maintenance` WRITE;
/*!40000 ALTER TABLE `device_maintenance` DISABLE KEYS */;
INSERT INTO `device_maintenance` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6);
/*!40000 ALTER TABLE `device_maintenance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maintenance`
--

DROP TABLE IF EXISTS `maintenance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maintenance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `deviceId` int NOT NULL,
  `describe` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `type` varchar(10) DEFAULT NULL,
  `startTime` date NOT NULL,
  `endTime` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `maintenance_id_uindex` (`id`),
  KEY `maintenance_device_id_fk` (`deviceId`),
  CONSTRAINT `maintenance_device_id_fk` FOREIGN KEY (`deviceId`) REFERENCES `device` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintenance`
--

LOCK TABLES `maintenance` WRITE;
/*!40000 ALTER TABLE `maintenance` DISABLE KEYS */;
INSERT INTO `maintenance` VALUES (1,1,'维护描述1','维护计划1','小修','2024-01-01','2024-01-02'),(2,1,'维护描述2','维护计划2','中修','2024-02-01','2024-02-07'),(3,3,'维护描述3','维护计划3','小修','2024-03-01','2024-03-04'),(4,4,'维护描述4','维护计划4','小修','2024-04-01','2024-04-02'),(5,5,'维护描述5','维护计划5','中修','2024-05-01','2024-05-09'),(6,6,'维护描述6','维护计划6','中修','2024-06-01','2024-06-17'),(7,2,'维护描述7','维护计划7','大修','2024-07-01','2024-08-17');
/*!40000 ALTER TABLE `maintenance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `workHours` int DEFAULT NULL,
  `departmentId` int DEFAULT NULL,
  `title` varchar(50) NOT NULL,
  `birth` date DEFAULT NULL,
  `politicalOutlook` varchar(50) DEFAULT NULL,
  `sex` varchar(20) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `staff_if_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'张三',392,1,'经理','1985-01-01','党员','男','password1'),(2,'李四',56,1,'员工','1987-02-02','群众','女','password2'),(3,'王五',32,2,'经理','1986-03-03','团员','男','password3'),(4,'赵六',8,2,'员工','1988-04-04','群众','女','password4'),(5,'孙七',72,3,'经理','1984-05-05','党员','男','password5'),(6,'周八',136,3,'员工','1989-06-06','群众','女','password6');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id` int NOT NULL AUTO_INCREMENT,
  `supplierName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `supplier_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'供应商A'),(2,'供应商B'),(3,'供应商C'),(4,'供应商D'),(5,'供应商E'),(6,'供应商F');
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `typeName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type`
--

LOCK TABLES `type` WRITE;
/*!40000 ALTER TABLE `type` DISABLE KEYS */;
INSERT INTO `type` VALUES (1,'类型A'),(2,'类型B'),(3,'类型C'),(4,'类型D'),(5,'类型E'),(6,'类型F');
/*!40000 ALTER TABLE `type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work_order`
--

DROP TABLE IF EXISTS `work_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `work_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `maintenanceId` int DEFAULT NULL,
  `startTime` date NOT NULL,
  `endTime` date DEFAULT NULL,
  `hours` int NOT NULL,
  `workerId` int NOT NULL,
  `workRecord` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `work_order_id_uindex` (`id`),
  KEY `work_order_maintenance_id_fk` (`maintenanceId`),
  KEY `work_order_staff_id_fk` (`workerId`),
  CONSTRAINT `work_order_maintenance_id_fk` FOREIGN KEY (`maintenanceId`) REFERENCES `maintenance` (`id`),
  CONSTRAINT `work_order_staff_id_fk` FOREIGN KEY (`workerId`) REFERENCES `staff` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work_order`
--

LOCK TABLES `work_order` WRITE;
/*!40000 ALTER TABLE `work_order` DISABLE KEYS */;
INSERT INTO `work_order` VALUES (1,1,'2024-01-01','2024-01-02',8,1,'完成维修任务1','无'),(2,2,'2024-02-01','2024-01-07',56,2,'完成维修任务2','无'),(3,3,'2024-03-01','2024-03-04',32,3,'完成维修任务3','无'),(4,4,'2024-04-01','2024-04-02',8,4,'完成维修任务4','无'),(5,5,'2024-05-01','2024-05-09',72,5,'完成维修任务5','无'),(6,6,'2024-06-01','2024-06-17',136,6,'完成维修任务6','无'),(7,7,'2024-07-01','2024-08-17',384,1,'完成维修任务7',NULL);
/*!40000 ALTER TABLE `work_order` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-19 22:09:40
