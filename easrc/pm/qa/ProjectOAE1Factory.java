package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectOAE1Factory
{
    private ProjectOAE1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.qa.IProjectOAE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IProjectOAE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("61EB6915") ,com.kingdee.eas.port.pm.qa.IProjectOAE1.class);
    }
    
    public static com.kingdee.eas.port.pm.qa.IProjectOAE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IProjectOAE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("61EB6915") ,com.kingdee.eas.port.pm.qa.IProjectOAE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.qa.IProjectOAE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IProjectOAE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("61EB6915"));
    }
    public static com.kingdee.eas.port.pm.qa.IProjectOAE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IProjectOAE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("61EB6915"));
    }
}