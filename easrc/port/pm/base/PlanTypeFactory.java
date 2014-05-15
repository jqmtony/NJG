package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PlanTypeFactory
{
    private PlanTypeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IPlanType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IPlanType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1600D144") ,com.kingdee.eas.port.pm.base.IPlanType.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IPlanType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IPlanType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1600D144") ,com.kingdee.eas.port.pm.base.IPlanType.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IPlanType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IPlanType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1600D144"));
    }
    public static com.kingdee.eas.port.pm.base.IPlanType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IPlanType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1600D144"));
    }
}