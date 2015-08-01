package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectDynaticFundPayPlanFactory
{
    private ProjectDynaticFundPayPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("078223D0") ,com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlan.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("078223D0") ,com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("078223D0"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("078223D0"));
    }
}