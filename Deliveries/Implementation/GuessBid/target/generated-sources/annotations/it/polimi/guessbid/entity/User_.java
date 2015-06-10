package it.polimi.guessbid.entity;

import it.polimi.guessbid.entity.Auction;
import it.polimi.guessbid.entity.Bid;
import it.polimi.guessbid.entity.Notification;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-10T18:06:16")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile CollectionAttribute<User, Auction> auctionCollection;
    public static volatile CollectionAttribute<User, Notification> notificationCollection;
    public static volatile SingularAttribute<User, String> password;
    public static volatile CollectionAttribute<User, Bid> bidCollection;
    public static volatile SingularAttribute<User, Float> credit;
    public static volatile SingularAttribute<User, Integer> userId;
    public static volatile SingularAttribute<User, String> groupname;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> username;

}