package com.kingdee.eas.fdc.aimcost.report.projectdynamic;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectDynamicCostFacadeFactory
{
    private ProjectDynamicCostFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.report.projectdynamic.IProjectDynamicCostFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.projectdynamic.IProjectDynamicCostFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5EEE7752") ,com.kingdee.eas.fdc.aimcost.report.projectdynamic.IProjectDynamicCostFacade.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.report.projectdynamic.IProjectDynamicCostFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.projectdynamic.IProjectDynamicCostFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5EEE7752") ,com.kingdee.eas.fdc.aimcost.report.projectdynamic.IProjectDynamicCostFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.report.projectdynamic.IProjectDynamicCostFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.projectdynamic.IProjectDynamicCostFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5EEE7752"));
    }
    public static com.kingdee.eas.fdc.aimcost.report.projectdynamic.IProjectDynamicCostFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.report.projectdynamic.IProjectDynamicCostFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5EEE7752"));
    }
}