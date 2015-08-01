package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectWithCostCenterOUFactory
{
    private ProjectWithCostCenterOUFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D8D38DD5") ,com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D8D38DD5") ,com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D8D38DD5"));
    }
    public static com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectWithCostCenterOU)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D8D38DD5"));
    }
}