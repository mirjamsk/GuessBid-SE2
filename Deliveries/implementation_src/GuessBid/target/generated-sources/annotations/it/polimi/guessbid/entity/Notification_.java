package it.polimi.guessbid.entity;

import it.polimi.guessbid.entity.Auction;
import it.polimi.guessbid.entity.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-22T13:21:59")
@StaticMetamodel(Notification.class)
public class Notification_ { 

    public static volatile SingularAttribute<Notification, Boolean> isOutcome;
    public static volatile SingularAttribute<Notification, String> description;
    public static volatile SingularAttribute<Notification, Integer> rank;
    public static volatile SingularAttribute<Notification, User> nUserId;
    public static volatile SingularAttribute<Notification, Integer> notificationId;
    public static volatile SingularAttribute<Notification, Date> timestamp;
    public static volatile SingularAttribute<Notification, Auction> nAuctionId;

}