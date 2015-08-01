package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimCostCtrlItemFactory
{
    private AimCostCtrlItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("84F486B9") ,com.kingdee.eas.fdc.aimcost.IAimCostCtrlItem.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("84F486B9") ,com.kingdee.eas.fdc.aimcost.IAimCostCtrlItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("84F486B9"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("84F486B9"));
    }
}