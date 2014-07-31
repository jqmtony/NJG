package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectBudget2E1Factory
{
    private ProjectBudget2E1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudget2E1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudget2E1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D23C659D") ,com.kingdee.eas.port.pm.invest.IProjectBudget2E1.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IProjectBudget2E1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudget2E1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D23C659D") ,com.kingdee.eas.port.pm.invest.IProjectBudget2E1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudget2E1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudget2E1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D23C659D"));
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudget2E1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudget2E1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D23C659D"));
    }
}