package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynamicCostMonitorFactory
{
    private DynamicCostMonitorFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostMonitor getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostMonitor)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("272DF0A5") ,com.kingdee.eas.fdc.aimcost.IDynamicCostMonitor.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostMonitor getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostMonitor)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("272DF0A5") ,com.kingdee.eas.fdc.aimcost.IDynamicCostMonitor.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostMonitor getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostMonitor)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("272DF0A5"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostMonitor getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostMonitor)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("272DF0A5"));
    }
}