CREATE DEFINER=`root`@`localhost` PROCEDURE `get_user_auction_ranking`(IN arg_user_id INT, IN arg_auction_id INT)
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
END