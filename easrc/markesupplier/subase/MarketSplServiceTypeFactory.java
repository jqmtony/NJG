package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSplServiceTypeFactory
{
    private MarketSplServiceTypeFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplServiceType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplServiceType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("01B92552") ,com.kingdee.eas.port.markesupplier.subase.IMarketSplServiceType.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplServiceType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplServiceType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("01B92552") ,com.kingdee.eas.port.markesupplier.subase.IMarketSplServiceType.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplServiceType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplServiceType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("01B92552"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplServiceType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplServiceType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("01B92552"));
    }
}