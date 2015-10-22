package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectOAE2Factory
{
    private ProjectOAE2Factory()
    {
    }
    public static com.kingdee.eas.port.pm.qa.IProjectOAE2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IProjectOAE2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("61EB6916") ,com.kingdee.eas.port.pm.qa.IProjectOAE2.class);
    }
    
    public static com.kingdee.eas.port.pm.qa.IProjectOAE2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IProjectOAE2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("61EB6916") ,com.kingdee.eas.port.pm.qa.IProjectOAE2.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.qa.IProjectOAE2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IProjectOAE2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("61EB6916"));
    }
    public static com.kingdee.eas.port.pm.qa.IProjectOAE2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IProjectOAE2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("61EB6916"));
    }
}