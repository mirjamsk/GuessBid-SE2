CREATE DEFINER=`guessbid`@`%` PROCEDURE `get_auction_rankings`(IN arg_auction_id INT)
BEGIN
SELECT w.*, 1 AS rank
FROM winning_bid as w
WHERE w.bid_auction_id = arg_auction_id
UNION

SELECT * FROM 
 (SELECT  b.*, @rank:=@rank+1 AS rank
 FROM bid as b, (SELECT @rank := 1) AS r
 WHERE b.bid_auction_id = arg_auction_id and 
	b.bid_id not in (SELECT w.bid_id FROM winning_bid as w)  
    ORDER BY amount ASC) AS t;

END