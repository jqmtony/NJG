package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectDynaticFundPayPlanDetailEntryFactory
{
    private ProjectDynaticFundPayPlanDetailEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanDetailEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanDetailEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("04F78CB1") ,com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanDetailEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanDetailEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanDetailEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("04F78CB1") ,com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanDetailEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanDetailEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanDetailEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("04F78CB1"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanDetailEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanDetailEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("04F78CB1"));
    }
}