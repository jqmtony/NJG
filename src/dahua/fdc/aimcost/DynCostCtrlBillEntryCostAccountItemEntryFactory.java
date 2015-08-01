package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynCostCtrlBillEntryCostAccountItemEntryFactory
{
    private DynCostCtrlBillEntryCostAccountItemEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntryCostAccountItemEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntryCostAccountItemEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5360B310") ,com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntryCostAccountItemEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntryCostAccountItemEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntryCostAccountItemEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5360B310") ,com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntryCostAccountItemEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntryCostAccountItemEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntryCostAccountItemEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5360B310"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntryCostAccountItemEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntryCostAccountItemEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5360B310"));
    }
}