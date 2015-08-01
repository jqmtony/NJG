package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynCostCtrlBillEntryFactory
{
    private DynCostCtrlBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9EC82E11") ,com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9EC82E11") ,com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9EC82E11"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9EC82E11"));
    }
}