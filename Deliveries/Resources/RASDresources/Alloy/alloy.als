sig User {}

sig Auction{
	seller: one User,
	winningBid : lone Bid
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
// only a user with a winning bid can get winning notification
fact  {
		all w:AuctionWinnerNotification |
			w.user = w.linkedTo.winningBid.bidder
}
// only a bidder can get a rank notifiction
fact  {
		all r:AuctionRankNotification, u:User | some b:Bid |
			(r.user = u) =>( r.linkedTo = b.auction and b.bidder = u)
}
//all winning bids need to generate a winning notification
fact{
	all a:Auction | one w: AuctionWinnerNotification |
		(#a.winningBid =1 ) => ( w.linkedTo = a)
}
//PREDICATES
pred show() {}

run show for 4 but exactly 3 AuctionWinnerNotification,1 AuctionRankNotification, 5 User
