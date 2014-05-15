package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierReviewGatherEntryPFactory
{
    private MarketSupplierReviewGatherEntryPFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntryP getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntryP)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4A7122D9") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntryP.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntryP getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntryP)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4A7122D9") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntryP.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntryP getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntryP)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4A7122D9"));
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntryP getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierReviewGatherEntryP)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4A7122D9"));
    }
}