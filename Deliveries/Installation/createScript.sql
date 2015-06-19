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
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `guessbid_db`.`bid_BEFORE_INSERT` BEFORE INSERT ON `bid` FOR EACH ROW
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
CREATE DEFINER=`%`@`%` PROCEDURE `get_auction_rankings`(IN arg_auction_id INT)
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
CREATE DEFINER=`%`@`%` PROCEDURE `get_bidid_at_auction_rank`(IN arg_rank INT, IN arg_auction_id INT)
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
CREATE DEFINER=`%`@`%` PROCEDURE `get_distinct_auction_rankings`(IN arg_auction_id INT)
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
CREATE DEFINER=`%`@`%` PROCEDURE `get_distinct_user_auction_ranking`(IN arg_user_id INT, IN arg_auction_id INT)
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
CREATE DEFINER=`%`@`%` PROCEDURE `get_userid_at_auction_rank`(IN arg_rank INT, IN arg_auction_id INT)
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
CREATE DEFINER=`%`@`%` PROCEDURE `get_user_auction_ranking`(IN arg_user_id INT, IN arg_auction_id INT)
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
/*!50013 DEFINER=`%`@`%` SQL SECURITY DEFINER */
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
/*!50013 DEFINER=`%`@`%` SQL SECURITY DEFINER */
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
/*!50013 DEFINER=`%`@`%` SQL SECURITY DEFINER */
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

-- Dump completed on 2015-06-17 15:43:31
