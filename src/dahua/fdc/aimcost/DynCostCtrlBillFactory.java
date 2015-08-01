package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynCostCtrlBillFactory
{
    private DynCostCtrlBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9EE30EE1") ,com.kingdee.eas.fdc.aimcost.IDynCostCtrlBill.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9EE30EE1") ,com.kingdee.eas.fdc.aimcost.IDynCostCtrlBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9EE30EE1"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9EE30EE1"));
    }
}