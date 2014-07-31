package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectBudget2Factory
{
    private ProjectBudget2Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudget2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudget2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("91226D51") ,com.kingdee.eas.port.pm.invest.IProjectBudget2.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IProjectBudget2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudget2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("91226D51") ,com.kingdee.eas.port.pm.invest.IProjectBudget2.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudget2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudget2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("91226D51"));
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudget2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudget2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("91226D51"));
    }
}