package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AimDynCostGetFacadeFactory
{
    private AimDynCostGetFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IAimDynCostGetFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimDynCostGetFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("08BDF4F8") ,com.kingdee.eas.fdc.aimcost.IAimDynCostGetFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IAimDynCostGetFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimDynCostGetFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("08BDF4F8") ,com.kingdee.eas.fdc.aimcost.IAimDynCostGetFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IAimDynCostGetFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimDynCostGetFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("08BDF4F8"));
    }
    public static com.kingdee.eas.fdc.aimcost.IAimDynCostGetFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IAimDynCostGetFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("08BDF4F8"));
    }
}