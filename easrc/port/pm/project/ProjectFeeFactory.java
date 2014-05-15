package com.kingdee.eas.port.pm.project;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectFeeFactory
{
    private ProjectFeeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.project.IProjectFee getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.project.IProjectFee)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("50324398") ,com.kingdee.eas.port.pm.project.IProjectFee.class);
    }
    
    public static com.kingdee.eas.port.pm.project.IProjectFee getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.project.IProjectFee)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("50324398") ,com.kingdee.eas.port.pm.project.IProjectFee.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.project.IProjectFee getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.project.IProjectFee)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("50324398"));
    }
    public static com.kingdee.eas.port.pm.project.IProjectFee getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.project.IProjectFee)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("50324398"));
    }
}