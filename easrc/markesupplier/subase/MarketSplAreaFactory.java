package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSplAreaFactory
{
    private MarketSplAreaFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplArea getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplArea)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9E9AB82A") ,com.kingdee.eas.port.markesupplier.subase.IMarketSplArea.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplArea getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplArea)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9E9AB82A") ,com.kingdee.eas.port.markesupplier.subase.IMarketSplArea.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplArea getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplArea)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9E9AB82A"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplArea getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplArea)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9E9AB82A"));
    }
}