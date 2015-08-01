package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynamicCostMonitorContractEntryFactory
{
    private DynamicCostMonitorContractEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorContractEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorContractEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3A8F15DB") ,com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorContractEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorContractEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorContractEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3A8F15DB") ,com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorContractEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorContractEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorContractEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3A8F15DB"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorContractEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynamicCostMonitorContractEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3A8F15DB"));
    }
}