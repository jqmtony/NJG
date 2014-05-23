package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierReviewGatherFactory
{
    private MarketSupplierReviewGatherFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGather getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGather)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("869980DB") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGather.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGather getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGather)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("869980DB") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGather.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGather getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGather)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("869980DB"));
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGather getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGather)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("869980DB"));
    }
}