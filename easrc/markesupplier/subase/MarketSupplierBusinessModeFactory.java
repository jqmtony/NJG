package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierBusinessModeFactory
{
    private MarketSupplierBusinessModeFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierBusinessMode getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierBusinessMode)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("863FF2E1") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierBusinessMode.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierBusinessMode getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierBusinessMode)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("863FF2E1") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierBusinessMode.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierBusinessMode getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierBusinessMode)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("863FF2E1"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierBusinessMode getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierBusinessMode)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("863FF2E1"));
    }
}