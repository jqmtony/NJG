package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimCostCtrlCostActItemsFactory
{
    private AimCostCtrlCostActItemsFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlCostActItems getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlCostActItems)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3B2706E1") ,com.kingdee.eas.fdc.aimcost.IAimCostCtrlCostActItems.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlCostActItems getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlCostActItems)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3B2706E1") ,com.kingdee.eas.fdc.aimcost.IAimCostCtrlCostActItems.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlCostActItems getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlCostActItems)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3B2706E1"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlCostActItems getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlCostActItems)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3B2706E1"));
    }
}