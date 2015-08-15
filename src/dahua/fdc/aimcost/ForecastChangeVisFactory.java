package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ForecastChangeVisFactory
{
    private ForecastChangeVisFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IForecastChangeVis getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IForecastChangeVis)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B4926E1E") ,com.kingdee.eas.fdc.aimcost.IForecastChangeVis.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IForecastChangeVis getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IForecastChangeVis)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B4926E1E") ,com.kingdee.eas.fdc.aimcost.IForecastChangeVis.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IForecastChangeVis getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IForecastChangeVis)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B4926E1E"));
    }
    public static com.kingdee.eas.fdc.aimcost.IForecastChangeVis getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IForecastChangeVis)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B4926E1E"));
    }
}