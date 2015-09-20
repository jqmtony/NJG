package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ForecastChangeVisSplitEntryFactory
{
    private ForecastChangeVisSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.IForecastChangeVisSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IForecastChangeVisSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BECD9A36") ,com.kingdee.eas.fdc.aimcost.IForecastChangeVisSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.IForecastChangeVisSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IForecastChangeVisSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BECD9A36") ,com.kingdee.eas.fdc.aimcost.IForecastChangeVisSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.IForecastChangeVisSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IForecastChangeVisSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BECD9A36"));
    }
    public static com.kingdee.eas.fdc.aimcost.IForecastChangeVisSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.IForecastChangeVisSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BECD9A36"));
    }
}