package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketLevelSetUpFactory
{
    private MarketLevelSetUpFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketLevelSetUp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketLevelSetUp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5DDF6D6B") ,com.kingdee.eas.port.markesupplier.subase.IMarketLevelSetUp.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketLevelSetUp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketLevelSetUp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5DDF6D6B") ,com.kingdee.eas.port.markesupplier.subase.IMarketLevelSetUp.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketLevelSetUp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketLevelSetUp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5DDF6D6B"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketLevelSetUp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketLevelSetUp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5DDF6D6B"));
    }
}