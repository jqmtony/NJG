package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynCostCtrlEntryItemsFactory
{
    private DynCostCtrlEntryItemsFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlEntryItems getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlEntryItems)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CF9145C8") ,com.kingdee.eas.fdc.aimcost.IDynCostCtrlEntryItems.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlEntryItems getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlEntryItems)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CF9145C8") ,com.kingdee.eas.fdc.aimcost.IDynCostCtrlEntryItems.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlEntryItems getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlEntryItems)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CF9145C8"));
    }
    public static com.kingdee.eas.fdc.aimcost.IDynCostCtrlEntryItems getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IDynCostCtrlEntryItems)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CF9145C8"));
    }
}