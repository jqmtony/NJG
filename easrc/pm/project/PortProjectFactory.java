package com.kingdee.eas.port.pm.project;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PortProjectFactory
{
    private PortProjectFactory()
    {
    }
    public static com.kingdee.eas.port.pm.project.IPortProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.project.IPortProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6752A6CD") ,com.kingdee.eas.port.pm.project.IPortProject.class);
    }
    
    public static com.kingdee.eas.port.pm.project.IPortProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.project.IPortProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6752A6CD") ,com.kingdee.eas.port.pm.project.IPortProject.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.project.IPortProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.project.IPortProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6752A6CD"));
    }
    public static com.kingdee.eas.port.pm.project.IPortProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.project.IPortProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6752A6CD"));
    }
}