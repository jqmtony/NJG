package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ForecastChangeVisEntryFactory
{
    private ForecastChangeVisEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IForecastChangeVisEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IForecastChangeVisEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("42F3CCF4") ,com.kingdee.eas.fdc.aimcost.IForecastChangeVisEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IForecastChangeVisEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IForecastChangeVisEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("42F3CCF4") ,com.kingdee.eas.fdc.aimcost.IForecastChangeVisEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IForecastChangeVisEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IForecastChangeVisEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("42F3CCF4"));
    }
    public static com.kingdee.eas.fdc.aimcost.IForecastChangeVisEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IForecastChangeVisEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("42F3CCF4"));
    }
}