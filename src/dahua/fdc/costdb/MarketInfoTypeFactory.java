package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketInfoTypeFactory
{
    private MarketInfoTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.costdb.IMarketInfoType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IMarketInfoType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9914B046") ,com.kingdee.eas.fdc.costdb.IMarketInfoType.class);
    }
    
    public static com.kingdee.eas.fdc.costdb.IMarketInfoType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IMarketInfoType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9914B046") ,com.kingdee.eas.fdc.costdb.IMarketInfoType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costdb.IMarketInfoType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IMarketInfoType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9914B046"));
    }
    public static com.kingdee.eas.fdc.costdb.IMarketInfoType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IMarketInfoType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9914B046"));
    }
}