package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class YearInvestPlanE2Factory
{
    private YearInvestPlanE2Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlanE2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlanE2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5E406885") ,com.kingdee.eas.port.pm.invest.IYearInvestPlanE2.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlanE2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlanE2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5E406885") ,com.kingdee.eas.port.pm.invest.IYearInvestPlanE2.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlanE2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlanE2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5E406885"));
    }
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlanE2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlanE2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5E406885"));
    }
}