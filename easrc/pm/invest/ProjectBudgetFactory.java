package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectBudgetFactory
{
    private ProjectBudgetFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudget getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudget)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BA5BF301") ,com.kingdee.eas.port.pm.invest.IProjectBudget.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IProjectBudget getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudget)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BA5BF301") ,com.kingdee.eas.port.pm.invest.IProjectBudget.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudget getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudget)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BA5BF301"));
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudget getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudget)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BA5BF301"));
    }
}