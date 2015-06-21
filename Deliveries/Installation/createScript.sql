CREATE DATABASE  IF NOT EXISTS `guessbid_db` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `guessbid_db`;
-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: guessbid_db
-- ------------------------------------------------------
-- Server version	5.7.7-rc

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
-- Temporary view structure for view `active_auctions`
--

DROP TABLE IF EXISTS `active_auctions`;
/*!50001 DROP VIEW IF EXISTS `active_auctions`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `active_auctions` AS SELECT 
 1 AS `auction_id`,
 1 AS `seller_id`,
 1 AS `name`,
 1 AS `description`,
 1 AS `end_time`,
 1 AS `winning_bid_id`,
 1 AS `timestamp`,
 1 AS `category`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `auction`
--

DROP TABLE IF EXISTS `auction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auction` (
  `auction_id` int(11) NOT NULL AUTO_INCREMENT,
  `seller_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` mediumtext,
  `end_time` datetime NOT NULL,
  `winning_bid_id` int(11) DEFAULT NULL,
  `category` varchar(45) NOT NULL DEFAULT 'Other',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`auction_id`),
  KEY `seller_id_idx` (`seller_id`),
  KEY `winning_bid_id_idx` (`winning_bid_id`),
  CONSTRAINT `seller_id` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `winning_bid_id` FOREIGN KEY (`winning_bid_id`) REFERENCES `bid` (`bid_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bid`
--

DROP TABLE IF EXISTS `bid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bid` (
  `bid_id` int(11) NOT NULL AUTO_INCREMENT,
  `bidder_id` int(11) NOT NULL,
  `bid_auction_id` int(11) NOT NULL,
  `amount` float NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`bid_id`),
  KEY `bidder_id_idx` (`bidder_id`),
  KEY `bid_auction_id_idx` (`bid_auction_id`),
  CONSTRAINT `bid_auction_id` FOREIGN KEY (`bid_auction_id`) REFERENCES `auction` (`auction_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bidder_id` FOREIGN KEY (`bidder_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/  /*!50003 TRIGGER `guessbid_db`.`bid_BEFORE_INSERT` BEFORE INSERT ON `bid` FOR EACH ROW
BEGIN
	IF new.bidder_id = (SELECT seller_id FROM auction WHERE new.bid_auction_id = auction_id)
    THEN
    signal sqlstate '45000' set message_text = 'Seller cannot bid on own auction';
      
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `type_UNIQUE` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `finished_auctions`
--

DROP TABLE IF EXISTS `finished_auctions`;
/*!50001 DROP VIEW IF EXISTS `finished_auctions`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `finished_auctions` AS SELECT 
 1 AS `auction_id`,
 1 AS `seller_id`,
 1 AS `name`,
 1 AS `description`,
 1 AS `end_time`,
 1 AS `winning_bid_id`,
 1 AS `timestamp`,
 1 AS `category`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `n_user_id` int(11) NOT NULL,
  `n_auction_id` int(11) NOT NULL,
  `description` mediumtext,
  `is_outcome` tinyint(1) NOT NULL DEFAULT '0',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `rank` int(10) unsigned NOT NULL,
  PRIMARY KEY (`notification_id`),
  KEY `n_user_id_idx` (`n_user_id`),
  KEY `n_auction_id_idx` (`n_auction_id`),
  CONSTRAINT `n_auction_id` FOREIGN KEY (`n_auction_id`) REFERENCES `auction` (`auction_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `n_user_id` FOREIGN KEY (`n_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `credit` float unsigned NOT NULL DEFAULT '100',
  `email` varchar(255) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `groupname` varchar(45) NOT NULL DEFAULT 'USERS',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `winning_bid`
--

DROP TABLE IF EXISTS `winning_bid`;
/*!50001 DROP VIEW IF EXISTS `winning_bid`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `winning_bid` AS SELECT 
 1 AS `bid_id`,
 1 AS `bidder_id`,
 1 AS `bid_auction_id`,
 1 AS `amount`,
 1 AS `timestamp`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'guessbid_db'
--

--
-- Dumping routines for database 'guessbid_db'
--
/*!50003 DROP PROCEDURE IF EXISTS `get_auction_rankings` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `get_auction_rankings`(IN arg_auction_id INT)
BEGIN

SELECT w.*, 1 AS rank
FROM winning_bid as w
WHERE w.bid_auction_id = arg_auction_id
UNION

SELECT * FROM 
 (SELECT  b.*, @rank:=@rank+1 AS rank
 FROM bid as b, (SELECT @rank := 1) AS r
 WHERE b.bid_auction_id = arg_auction_id and 
	b.bid_id not in (SELECT w.bid_id FROM winning_bid as w  WHERE w.bid_auction_id = arg_auction_id)  
    ORDER BY amount ASC) AS t;

    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_bidid_at_auction_rank` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `get_bidid_at_auction_rank`(IN arg_rank INT, IN arg_auction_id INT)
BEGIN

SELECT w.bid_id
FROM winning_bid as w
WHERE w.bid_auction_id = arg_auction_id and arg_rank = 1
UNION

SELECT t.bid_id FROM 
 (SELECT  b.*, @rank:=@rank+1 AS rank
 FROM bid as b, (SELECT @rank := 1) AS r
 WHERE b.bid_auction_id = arg_auction_id and 
	b.bid_id not in (SELECT w.bid_id FROM winning_bid as w WHERE w.bid_auction_id = arg_auction_id)  
    ORDER BY amount ASC) AS t
WHERE t.rank = arg_rank;

    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_distinct_auction_rankings` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `get_distinct_auction_rankings`(IN arg_auction_id INT)
BEGIN

SELECT w.bidder_id, 1 AS rank
FROM winning_bid as w
WHERE w.bid_auction_id = arg_auction_id

UNION

SELECT t.bidder_id,  @rank:=@rank+1 AS rank
FROM (SELECT @rank := 1) AS r,
	 (SELECT  b.bidder_id
	  FROM bid as b
	  WHERE b.bid_auction_id = arg_auction_id and 
			b.bidder_id not in (SELECT w.bidder_id FROM winning_bid as w WHERE w.bid_auction_id = arg_auction_id)  
GROUP BY b.bidder_id
ORDER BY min(amount) ASC
) AS t;

    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_distinct_user_auction_ranking` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `get_distinct_user_auction_ranking`(IN arg_user_id INT, IN arg_auction_id INT)
BEGIN

SELECT 1 AS rank
FROM winning_bid as w
WHERE w.bid_auction_id = arg_auction_id and w.bidder_id = arg_user_id

UNION
 SELECT rank FROM(
SELECT br.bidder_id, @rank:=@rank+1 AS rank
FROM (SELECT @rank := 1) AS r,
	 (SELECT b.bidder_id
	  FROM bid as b
	  WHERE 
		b.bid_auction_id =  arg_auction_id and 
		b.bidder_id not in (SELECT w.bidder_id FROM winning_bid as w WHERE w.bid_auction_id = arg_auction_id)  
	GROUP BY b.bidder_id
	ORDER BY min(amount) ASC
 ) AS br) as u
    
WHERE u.bidder_id = arg_user_id

LIMIT 1;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_userid_at_auction_rank` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `get_userid_at_auction_rank`(IN arg_rank INT, IN arg_auction_id INT)
BEGIN

SELECT w.bidder_id
FROM winning_bid as w
WHERE w.bid_auction_id = arg_auction_id and arg_rank = 1
UNION

SELECT t.bidder_id FROM 
 (SELECT  b.*, @rank:=@rank+1 AS rank
 FROM bid as b, (SELECT @rank := 1) AS r
 WHERE b.bid_auction_id = arg_auction_id and 
	b.bid_id not in (SELECT w.bid_id FROM winning_bid as w WHERE w.bid_auction_id = arg_auction_id)  
    ORDER BY amount ASC) AS t
WHERE t.rank = arg_rank;

    
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_user_auction_ranking` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE PROCEDURE `get_user_auction_ranking`(IN arg_user_id INT, IN arg_auction_id INT)
BEGIN

SELECT 1 AS rank
FROM winning_bid as w
WHERE w.bid_auction_id = arg_auction_id and w.bidder_id = arg_user_id

UNION

SELECT rank FROM 
 (SELECT  b.bidder_id, @rank:=@rank+1 AS rank
 FROM bid as b, (SELECT @rank := 1) AS r
 WHERE b.bid_auction_id =  arg_auction_id and 
	b.bid_id not in (SELECT w.bid_id FROM winning_bid as w)  
    ORDER BY amount ASC) AS br
WHERE br.bidder_id = arg_user_id
LIMIT 1;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `active_auctions`
--

/*!50001 DROP VIEW IF EXISTS `active_auctions`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 SQL SECURITY DEFINER */
/*!50001 VIEW `active_auctions` AS select `auction`.`auction_id` AS `auction_id`,`auction`.`seller_id` AS `seller_id`,`auction`.`name` AS `name`,`auction`.`description` AS `description`,`auction`.`end_time` AS `end_time`,`auction`.`winning_bid_id` AS `winning_bid_id`,`auction`.`timestamp` AS `timestamp`,`auction`.`category` AS `category` from `auction` where (`auction`.`end_time` > now()) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `finished_auctions`
--

/*!50001 DROP VIEW IF EXISTS `finished_auctions`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 SQL SECURITY DEFINER */
/*!50001 VIEW `finished_auctions` AS select `auction`.`auction_id` AS `auction_id`,`auction`.`seller_id` AS `seller_id`,`auction`.`name` AS `name`,`auction`.`description` AS `description`,`auction`.`end_time` AS `end_time`,`auction`.`winning_bid_id` AS `winning_bid_id`,`auction`.`timestamp` AS `timestamp`,`auction`.`category` AS `category` from `auction` where (`auction`.`end_time` <= now()) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `winning_bid`
--

/*!50001 DROP VIEW IF EXISTS `winning_bid`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 SQL SECURITY DEFINER */
/*!50001 VIEW `winning_bid` AS select `b1`.`bid_id` AS `bid_id`,`b1`.`bidder_id` AS `bidder_id`,`b1`.`bid_auction_id` AS `bid_auction_id`,`b1`.`amount` AS `amount`,`b1`.`timestamp` AS `timestamp` from `bid` `b1` where ((`b1`.`bid_auction_id`,`b1`.`amount`) = (select `b2`.`bid_auction_id`,`b2`.`amount` from `bid` `b2` where (`b1`.`bid_auction_id` = `b2`.`bid_auction_id`) group by `b2`.`bid_auction_id`,`b2`.`amount` having (count(0) = 1) order by `b2`.`amount` limit 1)) */;
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

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Art and Antiques'),(2,'Electronics and Accessories'),(3,'Fashion'),(4,'Furniture'),(5,'Gift Cards and Coupons'),(6,'Health and Beauty'),(7,'Jewelry and Watches'),(8,'Literature'),(9,'Movies'),(10,'Music and Instruments'),(11,'Other'),(12,'Real Estate'),(13,'Services'),(14,'Sporting Goods'),(15,'Tickets and Experiences'),(16,'Toys and Hobbies'),(17,'Video Games and Consoles');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (73,97.7,'john@john.com','John','96d9632f363564cc3032521409cf22a852f2032eec099ed5967c0d000cec607a','USERS'),(74,102.3,'june@june.com','June','c0fa1ef864788c455b22e34d6b11eb282603eeec08b2b10d38c37683ef46952c','USERS');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

-- Dump completed on 2015-06-17 15:43:31
LOCK TABLES `auction` WRITE;
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
INSERT INTO `auction` VALUES (69,73,'Batman 1 millennium edition, DC Comics','Batman is a fictional character created by the artist Bob Kane and writer Bill Finger . A comic book superhero , Batman first appeared in Detective Comics #27 (May 1939), and since then has appeared primarily in publications by DC Comics . Originally referred to as \"the Bat-Man\" and still referred to at times as \"the Batman\", he is additionally known as the \"Caped Crusader\",the \"Dark Knight\", and the \"World\'s Greatest Detective,\" among other titles.','2015-07-31 15:42:00',NULL,'Literature','2015-06-20 13:48:45'),(70,74,'New Beginners Acoustic Guitar','Best Choice Products presents to you this brand new acoustic guitar. This guitar is a combo package that comes with a guitar case, extra set of strings, pick, and a tuner. You’ll be playing your favorite tunes in no time with practice and dedication. We purchase all of our guitars directly from the manufacturer, providing you with the best prices available online.\r\nNEW PRODUCT WITH FACTORY PACKAGING\r\n\r\nFEATURES:\r\nFull wood construction\r\nShoulder Strap\r\nExtra set of strings\r\nGreat gloss finish\r\nEasy to use tuner\r\nNice carrying bag\r\n38 inches long\r\nSteel strings\r\nGuitar pick\r\nPerfect for beginners ','2015-06-20 19:31:31',NULL,'Music and Instruments','2015-06-20 17:30:27'),(71,73,'Cold Cream','Ultra-rich, creamy formula offers intense hydration to seal in moisture and restore the skin\'s natural barrier. The nourishing virtues of white beeswax combined with the soothing and softening properties of Avene Thermal Spring Water gives this cream the power to restore the skin\'s hydrolipidic film and form a genuine barrier against environmental aggressors.','2015-06-20 19:48:30',NULL,'Health and Beauty','2015-06-20 17:46:51');
/*!40000 ALTER TABLE `auction` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `bid` WRITE;
/*!40000 ALTER TABLE `bid` DISABLE KEYS */;
INSERT INTO `bid` VALUES (1,73,70,2.3,'2015-06-20 17:30:41');
/*!40000 ALTER TABLE `bid` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,73,70,'Your new rank is ',0,'2015-06-20 17:30:41',1),(2,73,70,'Congrads, you won. The seller\'s email is june@june.com',1,'2015-06-20 17:31:31',1),(3,74,70,'Your auction has finished. The winning user is john@john.com',1,'2015-06-20 17:31:31',1),(4,73,71,'Sorry, looks like nobody won your auction',1,'2015-06-20 17:48:30',0);
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;



