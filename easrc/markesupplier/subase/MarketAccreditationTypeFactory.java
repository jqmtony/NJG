package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketAccreditationTypeFactory
{
    private MarketAccreditationTypeFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketAccreditationType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketAccreditationType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5095A062") ,com.kingdee.eas.port.markesupplier.subase.IMarketAccreditationType.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketAccreditationType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketAccreditationType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5095A062") ,com.kingdee.eas.port.markesupplier.subase.IMarketAccreditationType.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketAccreditationType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketAccreditationType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5095A062"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketAccreditationType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketAccreditationType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5095A062"));
    }
}