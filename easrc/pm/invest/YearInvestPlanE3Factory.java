package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class YearInvestPlanE3Factory
{
    private YearInvestPlanE3Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlanE3 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlanE3)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5E406886") ,com.kingdee.eas.port.pm.invest.IYearInvestPlanE3.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlanE3 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlanE3)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5E406886") ,com.kingdee.eas.port.pm.invest.IYearInvestPlanE3.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlanE3 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlanE3)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5E406886"));
    }
    public static com.kingdee.eas.port.pm.invest.IYearInvestPlanE3 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IYearInvestPlanE3)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5E406886"));
    }
}