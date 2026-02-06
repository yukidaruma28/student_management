mysqldump: [Warning] Using a password on the command line interface can be insecure.
-- MySQL dump 10.13  Distrib 8.0.42, for macos14.7 (arm64)
--
-- Host: localhost    Database: students_management
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `application_status`
--

DROP TABLE IF EXISTS `application_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_status` (
  `application_status_id` int NOT NULL AUTO_INCREMENT,
  `students_courses_id` int NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`application_status_id`),
  KEY `students_courses_id` (`students_courses_id`),
  CONSTRAINT `application_status_ibfk_1` FOREIGN KEY (`students_courses_id`) REFERENCES `students_courses` (`students_courses_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_status`
--

LOCK TABLES `application_status` WRITE;
/*!40000 ALTER TABLE `application_status` DISABLE KEYS */;
INSERT INTO `application_status` VALUES (1,1,'本申込'),(2,2,'仮申込'),(3,3,'仮申込'),(4,4,'仮申込'),(5,5,'仮申込'),(6,6,'仮申込'),(7,7,'仮申込'),(8,8,'仮申込'),(9,9,'仮申込'),(10,10,'仮申込'),(11,11,'仮申込'),(12,12,'仮申込'),(13,13,'仮申込'),(14,14,'仮申込'),(15,15,'仮申込'),(16,16,'仮申込'),(17,17,'仮申込'),(18,18,'仮申込'),(19,19,'仮申込'),(20,20,'仮申込'),(21,21,'仮申込'),(22,22,'仮申込'),(23,23,'仮申込'),(24,24,'仮申込'),(25,25,'仮申込'),(26,26,'仮申込'),(27,27,'仮申込'),(32,28,'仮申込');
/*!40000 ALTER TABLE `application_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_master`
--

DROP TABLE IF EXISTS `course_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_master` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `course_name` (`course_name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_master`
--

LOCK TABLES `course_master` WRITE;
/*!40000 ALTER TABLE `course_master` DISABLE KEYS */;
INSERT INTO `course_master` VALUES (1,'Javaコース','Java基礎から応用まで',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(2,'Webアプリ開発入門','Web開発の基礎',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(3,'MySQL応用','データベース設計と最適化',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(4,'Spring Boot超入門','Spring Bootフレームワーク入門',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(5,'フロントエンド開発','HTML/CSS/JavaScript',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(6,'ネットワーク基礎','ネットワークの基本概念',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(7,'API設計と実践','RESTful API設計',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(8,'セキュリティ基礎','Webセキュリティの基礎',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(9,'クラウド基礎講座','AWSやGCPの基礎',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(10,'DevOps実践','CI/CD構築',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(11,'GitとGitHub入門','バージョン管理の基礎',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(12,'JUnitテスト基礎','ユニットテストの書き方',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(13,'デザインパターン入門','GoFデザインパターン',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(14,'MySQLデータベース基礎','SQL基礎とDB操作',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(15,'Java基礎コース','Java言語の基礎',1,'2026-02-06 01:57:32','2026-02-06 01:57:32'),(16,'Java基礎コース123','Java言語の基礎（旧）',1,'2026-02-06 01:57:32','2026-02-06 01:57:32');
/*!40000 ALTER TABLE `course_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `studentId` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `furigana` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `city_name` varchar(255) NOT NULL,
  `age` int NOT NULL,
  `gender` enum('男性','女性','その他') NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`studentId`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'田中 太郎','たなか たろう','タロ','tanaka.taro@example.com','東京',25,'男性','特になし',1),(2,'鈴木 花子','すずき はなこ','はな','suzuki.hana@example.com','神奈川',299,'女性','',0),(3,'佐藤 次郎','さとう じろう','ジロ','sato.jiro@example.com','千葉',20,'男性','',0),(4,'高橋 愛','たかはし あい','アイちゃん','takahashi.ai@example.com','埼玉',23,'女性','',0),(5,'山本 拓海','やまもと たくみ','タク','yamamoto.takumi@example.com','大阪',21,'その他',NULL,0),(6,'井上 美咲','いのうえ みさき','ミサミサ','inoue.misaki@example.com','東京',17,'女性',NULL,0),(7,'大野 翔太','おおの しょうた','ショウ','ono.shota@example.com','神奈川',19,'男性','',0),(8,'藤田 翼','ふじた つばさ','ツバサ','fujita.tsubasa@example.com','埼玉',39,'男性','',0),(9,'西川 結衣','にしかわ ゆい','ユイちゃん','nishikawa.yui@example.com','千葉',34,'女性',NULL,0),(10,'加藤 剛','かとう ごう','ゴウさん','kato.gou@example.com','大阪',41,'男性',NULL,0),(11,'桜井 桃香','さくらい ももか','モモ','sakurai.momoka@example.com','東京',15,'女性',NULL,0),(12,'柴田 真司','しばた しんじ','シン','shibata.shinji@example.com','神奈川',44,'男性',NULL,0),(13,'長谷川 美月','はせがわ みつき','ミツキ','hasegawa.mitsuki@example.com','福岡',31,'女性',NULL,0),(14,'村上 大地','むらかみ だいち','ダイチ','murakami.daichi@example.com','北海道',38,'男性',NULL,0),(15,'山崎 恵子','やまざき けいこ','ケイさん','yamazaki.keiko@example.com','愛知',42,'女性',NULL,0),(16,'test16','てすと16','','test@ezweb','amerika',19,'男性','',0),(17,'17test','てすと17','','てすと17@example','canada',15,'男性','',0),(21,'test','test','test','test','test',29,'男性','',0),(22,'test','test','test','test','test',29,'男性','',0),(23,'test22','test22','test22','22test','test',29,'男性','',0),(24,'test','test','test','test','test',20,'男性','',0),(30,'qwer','qwer','','qwer','qwer',222,'男性','',0),(31,'9999','9999','','9999','9999',9999,'男性','',0),(32,'松本 緑茶','たなか むぎお','タロちゃん','tanaka.taro@example.com','東京',30,'男性','特になし',0),(35,'松本 緑茶','たなか むぎお','タロちゃん','tanaka.taro@example.com','東京',30,'男性','特になし',0),(36,'緑　茶','ぎお','タロちゃん','tanaka.taro@example.com','東京',30,'男性','特になし',0),(48,'緑茶','ぎお','タロちゃん','tanaka.taro@example.com','東京',30,'男性','特になし',0),(49,'テスト太郎','てすとたろう','テスト','test@example.com','東京都',25,'男性','テストユーザー',0),(50,'高橋太郎','たかはしたろう','たかはし','taro@ezweb.com','tokyo',26,'男性','',0);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students_courses`
--

DROP TABLE IF EXISTS `students_courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students_courses` (
  `students_courses_id` int NOT NULL AUTO_INCREMENT,
  `studentId` varchar(36) DEFAULT NULL,
  `select_courses` varchar(255) NOT NULL,
  `start_date` timestamp NOT NULL,
  `end_date` timestamp NOT NULL,
  `student_course_status` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`students_courses_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students_courses`
--

LOCK TABLES `students_courses` WRITE;
/*!40000 ALTER TABLE `students_courses` DISABLE KEYS */;
INSERT INTO `students_courses` VALUES (1,'1','JUnitテスト基礎','2025-04-30 15:00:00','2025-07-31 15:00:00','本申込'),(2,'2','Webアプリ開発入門','2025-05-09 15:00:00','2025-09-09 15:00:00','仮申込'),(3,'3','MySQL応用','2025-05-31 15:00:00','2025-09-29 15:00:00','仮申込'),(4,'4','Spring Boot超入門','2025-06-04 15:00:00','2025-10-04 15:00:00','仮申込'),(5,'5','フロントエンド開発','2025-06-30 15:00:00','2025-10-29 15:00:00','仮申込'),(6,'6','ネットワーク基礎','2025-07-09 15:00:00','2025-11-09 15:00:00','仮申込'),(7,'7','API設計と実践','2025-07-31 15:00:00','2025-11-30 15:00:00','仮申込'),(8,'8','セキュリティ基礎','2025-08-09 15:00:00','2025-12-09 15:00:00','仮申込'),(9,'9','クラウド基礎講座','2025-08-31 15:00:00','2025-12-31 15:00:00','仮申込'),(10,'10','DevOps実践','2025-09-09 15:00:00','2026-01-09 15:00:00','仮申込'),(11,'11','Java基礎コース','2025-04-30 15:00:00','2025-07-31 15:00:00','仮申込'),(12,'12','Java基礎コース','2025-04-30 15:00:00','2025-07-31 15:00:00','仮申込'),(13,'13','GitとGitHub入門','2025-05-14 15:00:00','2025-08-14 15:00:00','仮申込'),(14,'14','JUnitテスト基礎','2025-05-31 15:00:00','2025-08-31 15:00:00','仮申込'),(15,'15','デザインパターン入門','2025-06-09 15:00:00','2025-09-09 15:00:00','仮申込'),(16,'16','MySQLデータベース基礎','2025-06-19 15:00:00','2025-09-19 15:00:00','仮申込'),(17,'17','API設計と実践','2025-06-30 15:00:00','2025-09-30 15:00:00','仮申込'),(18,'18','Spring Boot超入門','2025-07-14 15:00:00','2025-11-14 15:00:00','仮申込'),(19,'19','フロントエンド開発','2025-07-31 15:00:00','2025-11-29 15:00:00','仮申込'),(20,'20','クラウド基礎講座','2025-08-14 15:00:00','2025-12-14 15:00:00','仮申込'),(21,'21','TESTコース','2025-05-02 06:07:12','2026-05-02 06:07:12','仮申込'),(22,'22','9999コース','2025-05-02 15:21:57','2026-05-02 15:21:57','仮申込'),(23,'32','動画編集コース','2025-05-03 19:23:24','2025-05-03 19:23:24','仮申込'),(24,'35','動画編集コース','2025-05-03 22:42:11','2026-05-03 22:42:11','仮申込'),(25,'36','動画編集コース','2025-05-03 23:32:03','2026-05-03 23:32:03','仮申込'),(26,'48','動画編集コース','2025-05-24 05:24:56','2026-05-24 05:24:56','仮申込'),(27,'49','Javaコース','2026-02-04 22:48:26','2027-02-04 22:48:26','仮申込'),(28,'50','GitとGitHub入門','2026-02-05 17:18:05','2027-02-05 17:18:05','仮申込');
/*!40000 ALTER TABLE `students_courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES (1,'aaaaa'),(2,'test'),(3,'hogehoge'),(4,'なかた'),(5,'たかし');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-06 21:22:42
