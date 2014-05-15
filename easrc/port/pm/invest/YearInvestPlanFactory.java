package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class YearInvestPlanFactory
{
    private YearInvestPlanFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5047FDF8") ,com.kingdee.eas.port.pm.invest.IYearInvestPlan.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5047FDF8") ,com.kingdee.eas.port.pm.invest.IYearInvestPlan.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5047FDF8"));
    }
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5047FDF8"));
    }
}