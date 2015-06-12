package it.polimi.guessbid.entity;

import it.polimi.guessbid.entity.Auction;
import it.polimi.guessbid.entity.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-12T15:32:22")
@StaticMetamodel(Bid.class)
public class Bid_ { 

    public static volatile CollectionAttribute<Bid, Auction> auctionCollection;
    public static volatile SingularAttribute<Bid, Float> amount;
    public static volatile SingularAttribute<Bid, User> bidderId;
    public static volatile SingularAttribute<Bid, Integer> bidId;
    public static volatile SingularAttribute<Bid, Auction> bidAuctionId;
    public static volatile SingularAttribute<Bid, Date> timestamp;

}