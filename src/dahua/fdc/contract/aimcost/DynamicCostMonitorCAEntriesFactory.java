package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynamicCostMonitorCAEntriesFactory
{
    private DynamicCostMonitorCAEntriesFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorCAEntries getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorCAEntries)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1B46874D") ,com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorCAEntries.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorCAEntries getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorCAEntries)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1B46874D") ,com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorCAEntries.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorCAEntries getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorCAEntries)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1B46874D"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorCAEntries getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorCAEntries)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1B46874D"));
    }
}