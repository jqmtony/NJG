package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectDynamicCostFacadeFactory
{
    private ProjectDynamicCostFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IProjectDynamicCostFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IProjectDynamicCostFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9E876388") ,com.kingdee.eas.fdc.contract.IProjectDynamicCostFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IProjectDynamicCostFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IProjectDynamicCostFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9E876388") ,com.kingdee.eas.fdc.contract.IProjectDynamicCostFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IProjectDynamicCostFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IProjectDynamicCostFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9E876388"));
    }
    public static com.kingdee.eas.fdc.contract.IProjectDynamicCostFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IProjectDynamicCostFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9E876388"));
    }
}