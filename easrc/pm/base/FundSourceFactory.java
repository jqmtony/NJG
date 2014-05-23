package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FundSourceFactory
{
    private FundSourceFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IFundSource getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IFundSource)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("77CCC241") ,com.kingdee.eas.port.pm.base.IFundSource.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IFundSource getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IFundSource)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("77CCC241") ,com.kingdee.eas.port.pm.base.IFundSource.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IFundSource getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IFundSource)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("77CCC241"));
    }
    public static com.kingdee.eas.port.pm.base.IFundSource getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IFundSource)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("77CCC241"));
    }
}