package it.polimi.registration.business.security.entity;

import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-22T11:03:21")
@StaticMetamodel(Bid.class)
public class Bid_ { 

    public static volatile CollectionAttribute<Bid, Auction> auctionCollection;
    public static volatile SingularAttribute<Bid, Float> amount;
    public static volatile SingularAttribute<Bid, User> bidderId;
    public static volatile SingularAttribute<Bid, Integer> bidId;
    public static volatile SingularAttribute<Bid, Auction> bidAuctionId;
    public static volatile SingularAttribute<Bid, Date> timestamp;

}