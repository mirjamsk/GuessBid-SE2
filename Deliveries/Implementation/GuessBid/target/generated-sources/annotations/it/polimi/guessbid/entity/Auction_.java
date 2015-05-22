package it.polimi.guessbid.entity;

import it.polimi.guessbid.entity.Bid;
import it.polimi.guessbid.entity.Notification;
import it.polimi.guessbid.entity.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-22T14:45:35")
@StaticMetamodel(Auction.class)
public class Auction_ { 

    public static volatile SingularAttribute<Auction, Integer> auctionId;
    public static volatile CollectionAttribute<Auction, Notification> notificationCollection;
    public static volatile SingularAttribute<Auction, User> sellerId;
    public static volatile SingularAttribute<Auction, String> name;
    public static volatile SingularAttribute<Auction, String> description;
    public static volatile CollectionAttribute<Auction, Bid> bidCollection;
    public static volatile SingularAttribute<Auction, Date> endTime;
    public static volatile SingularAttribute<Auction, String> category;
    public static volatile SingularAttribute<Auction, Bid> winningBidId;
    public static volatile SingularAttribute<Auction, Date> timestamp;

}