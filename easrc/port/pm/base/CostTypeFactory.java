package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostTypeFactory
{
    private CostTypeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.ICostType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICostType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8D5003E8") ,com.kingdee.eas.port.pm.base.ICostType.class);
    }
    
    public static com.kingdee.eas.port.pm.base.ICostType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICostType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8D5003E8") ,com.kingdee.eas.port.pm.base.ICostType.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.ICostType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICostType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8D5003E8"));
    }
    public static com.kingdee.eas.port.pm.base.ICostType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ICostType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8D5003E8"));
    }
}