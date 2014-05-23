package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostTypeTreeFactory
{
    private CostTypeTreeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.ICostTypeTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICostTypeTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9C83B7A6") ,com.kingdee.eas.port.pm.base.ICostTypeTree.class);
    }
    
    public static com.kingdee.eas.port.pm.base.ICostTypeTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICostTypeTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9C83B7A6") ,com.kingdee.eas.port.pm.base.ICostTypeTree.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.ICostTypeTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICostTypeTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9C83B7A6"));
    }
    public static com.kingdee.eas.port.pm.base.ICostTypeTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICostTypeTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9C83B7A6"));
    }
}