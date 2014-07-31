package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class YearInvestPlanEntryFactory
{
    private YearInvestPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2960155A") ,com.kingdee.eas.port.pm.invest.IYearInvestPlanEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2960155A") ,com.kingdee.eas.port.pm.invest.IYearInvestPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2960155A"));
    }
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2960155A"));
    }
}