package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimAimCostAdjustEntryFactory
{
    private AimAimCostAdjustEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimAimCostAdjustEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimAimCostAdjustEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("40E1453F") ,com.kingdee.eas.fdc.aimcost.IAimAimCostAdjustEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAimAimCostAdjustEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimAimCostAdjustEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("40E1453F") ,com.kingdee.eas.fdc.aimcost.IAimAimCostAdjustEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimAimCostAdjustEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimAimCostAdjustEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("40E1453F"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimAimCostAdjustEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimAimCostAdjustEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("40E1453F"));
    }
}