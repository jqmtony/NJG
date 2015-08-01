package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimAimCostAdjustFactory
{
    private AimAimCostAdjustFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimAimCostAdjust getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimAimCostAdjust)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("04C7B2F3") ,com.kingdee.eas.fdc.aimcost.IAimAimCostAdjust.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAimAimCostAdjust getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimAimCostAdjust)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("04C7B2F3") ,com.kingdee.eas.fdc.aimcost.IAimAimCostAdjust.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimAimCostAdjust getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimAimCostAdjust)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("04C7B2F3"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimAimCostAdjust getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimAimCostAdjust)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("04C7B2F3"));
    }
}