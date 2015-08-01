package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectDynaticFundPayPlanEntryFactory
{
    private ProjectDynaticFundPayPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7A4BD682") ,com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7A4BD682") ,com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7A4BD682"));
    }
    public static com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IProjectDynaticFundPayPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7A4BD682"));
    }
}