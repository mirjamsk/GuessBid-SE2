package it.polimi.registration.business.security.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-21T22:13:09")
@StaticMetamodel(ActiveAuctions.class)
public class ActiveAuctions_ { 

    public static volatile SingularAttribute<ActiveAuctions, Integer> auctionId;
    public static volatile SingularAttribute<ActiveAuctions, Integer> sellerId;
    public static volatile SingularAttribute<ActiveAuctions, String> name;
    public static volatile SingularAttribute<ActiveAuctions, String> description;
    public static volatile SingularAttribute<ActiveAuctions, Date> endTime;
    public static volatile SingularAttribute<ActiveAuctions, Integer> winningBidId;
    public static volatile SingularAttribute<ActiveAuctions, Date> timestamp;

}