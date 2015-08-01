package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimCostCtrlBillFactory
{
    private AimCostCtrlBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("84F12FAD") ,com.kingdee.eas.fdc.aimcost.IAimCostCtrlBill.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("84F12FAD") ,com.kingdee.eas.fdc.aimcost.IAimCostCtrlBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("84F12FAD"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimCostCtrlBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimCostCtrlBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("84F12FAD"));
    }
}