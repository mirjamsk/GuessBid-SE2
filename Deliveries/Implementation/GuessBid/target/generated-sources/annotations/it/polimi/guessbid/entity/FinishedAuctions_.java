package it.polimi.guessbid.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-10T18:06:16")
@StaticMetamodel(FinishedAuctions.class)
public class FinishedAuctions_ { 

    public static volatile SingularAttribute<FinishedAuctions, Integer> auctionId;
    public static volatile SingularAttribute<FinishedAuctions, Integer> sellerId;
    public static volatile SingularAttribute<FinishedAuctions, String> name;
    public static volatile SingularAttribute<FinishedAuctions, String> description;
    public static volatile SingularAttribute<FinishedAuctions, Date> endTime;
    public static volatile SingularAttribute<FinishedAuctions, String> category;
    public static volatile SingularAttribute<FinishedAuctions, Integer> winningBidId;
    public static volatile SingularAttribute<FinishedAuctions, Date> timestamp;

}