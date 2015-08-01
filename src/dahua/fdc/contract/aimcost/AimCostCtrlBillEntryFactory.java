package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimCostCtrlBillEntryFactory
{
    private AimCostCtrlBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("48AAACC5") ,com.kingdee.eas.fdc.aimcost.IAimCostCtrlBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("48AAACC5") ,com.kingdee.eas.fdc.aimcost.IAimCostCtrlBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("48AAACC5"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("48AAACC5"));
    }
}