package it.polimi.registration.business.security.entity;

import it.polimi.registration.business.security.entity.Auction;
import it.polimi.registration.business.security.entity.Bid;
import it.polimi.registration.business.security.entity.Notification;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-21T23:45:39")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile CollectionAttribute<User, Auction> auctionCollection;
    public static volatile CollectionAttribute<User, Notification> notificationCollection;
    public static volatile SingularAttribute<User, String> password;
    public static volatile CollectionAttribute<User, Bid> bidCollection;
    public static volatile SingularAttribute<User, Integer> credit;
    public static volatile SingularAttribute<User, Integer> userId;
    public static volatile SingularAttribute<User, String> groupname;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> username;

}