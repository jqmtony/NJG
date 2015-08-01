package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimAimCostItemFactory
{
    private AimAimCostItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimAimCostItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimAimCostItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A12F7F77") ,com.kingdee.eas.fdc.aimcost.IAimAimCostItem.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAimAimCostItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimAimCostItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A12F7F77") ,com.kingdee.eas.fdc.aimcost.IAimAimCostItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimAimCostItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimAimCostItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A12F7F77"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimAimCostItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimAimCostItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A12F7F77"));
    }
}