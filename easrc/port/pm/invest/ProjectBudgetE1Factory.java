package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectBudgetE1Factory
{
    private ProjectBudgetE1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudgetE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudgetE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("932B3F4D") ,com.kingdee.eas.port.pm.invest.IProjectBudgetE1.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IProjectBudgetE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudgetE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("932B3F4D") ,com.kingdee.eas.port.pm.invest.IProjectBudgetE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudgetE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudgetE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("932B3F4D"));
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudgetE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudgetE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("932B3F4D"));
    }
}