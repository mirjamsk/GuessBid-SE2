package it.polimi.guessbid.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-03T14:46:15")
@StaticMetamodel(WinningBid.class)
public class WinningBid_ { 

    public static volatile SingularAttribute<WinningBid, Float> amount;
    public static volatile SingularAttribute<WinningBid, Integer> bidderId;
    public static volatile SingularAttribute<WinningBid, Integer> bidId;
    public static volatile SingularAttribute<WinningBid, Integer> bidAuctionId;
    public static volatile SingularAttribute<WinningBid, Date> timestamp;

}