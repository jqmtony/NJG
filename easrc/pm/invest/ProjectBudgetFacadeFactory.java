package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectBudgetFacadeFactory
{
    private ProjectBudgetFacadeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudgetFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudgetFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1FC99E48") ,com.kingdee.eas.port.pm.invest.IProjectBudgetFacade.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IProjectBudgetFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudgetFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1FC99E48") ,com.kingdee.eas.port.pm.invest.IProjectBudgetFacade.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudgetFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudgetFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1FC99E48"));
    }
    public static com.kingdee.eas.port.pm.invest.IProjectBudgetFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectBudgetFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1FC99E48"));
    }
}