package it.polimi.registration.business.security.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-05-21T22:13:09")
@StaticMetamodel(FinishedAuctions.class)
public class FinishedAuctions_ { 

    public static volatile SingularAttribute<FinishedAuctions, Integer> auctionId;
    public static volatile SingularAttribute<FinishedAuctions, Integer> sellerId;
    public static volatile SingularAttribute<FinishedAuctions, String> name;
    public static volatile SingularAttribute<FinishedAuctions, String> description;
    public static volatile SingularAttribute<FinishedAuctions, Date> endTime;
    public static volatile SingularAttribute<FinishedAuctions, Integer> winningBidId;
    public static volatile SingularAttribute<FinishedAuctions, Date> timestamp;

}