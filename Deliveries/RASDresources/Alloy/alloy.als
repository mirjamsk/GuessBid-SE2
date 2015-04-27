sig User {}

sig Auction{
	seller: one User,
	winningBid : one Bid
}

sig Bid{
	auction: one Auction,
	bidder: one User
}

abstract sig Notification {
	user: one User,
	linkedTo: one Auction
}
sig AuctionRankNotification extends Notification{}
sig AuctionWinnerNotification extends Notification{}

//facts
fact everyAuctionHasOneSeller{
	all a: Auction | one u:User | a.seller = u
}
fact everyBidHasUniqueBidderAndAuction{
	all b: Bid | one u:User, a:Auction | b.auction = a and b.bidder = u
}
fact everyNofiticationHasUniqueUserAndAuction{
 	all n: Notification | one u: User, a:Auction | n.user = u and n.linkedTo = a
}
fact noSellerCanBidonOwnAuction{
	all u: User, a:Auction, b: Bid | ( u= a.seller  and a = b.auction ) => not( b.bidder = u )
}
fact everyAuctionHasLoneWinningNotification{
	all w1:AuctionWinnerNotification, w2:AuctionWinnerNotification | 
		(w1 != w2 )=> ( w1.linkedTo != w2.linkedTo)
}
fact winningBidCanOnlyBeOneOfPlacedBids{
	all a:Auction, b:Bid | 
	 	(b = a.winningBid ) => (a = b.auction)
}
//PREDICATES
pred show() {}

run show for 4
