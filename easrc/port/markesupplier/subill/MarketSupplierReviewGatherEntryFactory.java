package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierReviewGatherEntryFactory
{
    private MarketSupplierReviewGatherEntryFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("970BE857") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntry.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("970BE857") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("970BE857"));
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("970BE857"));
    }
}