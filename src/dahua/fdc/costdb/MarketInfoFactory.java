package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketInfoFactory
{
    private MarketInfoFactory()
    {
    }
    public static com.kingdee.eas.fdc.costdb.IMarketInfo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IMarketInfo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4FC002EC") ,com.kingdee.eas.fdc.costdb.IMarketInfo.class);
    }
    
    public static com.kingdee.eas.fdc.costdb.IMarketInfo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IMarketInfo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4FC002EC") ,com.kingdee.eas.fdc.costdb.IMarketInfo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costdb.IMarketInfo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IMarketInfo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4FC002EC"));
    }
    public static com.kingdee.eas.fdc.costdb.IMarketInfo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IMarketInfo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4FC002EC"));
    }
}