package com.kingdee.eas.port.pm.qa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectOAFactory
{
    private ProjectOAFactory()
    {
    }
    public static com.kingdee.eas.port.pm.qa.IProjectOA getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IProjectOA)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FD2BEEC9") ,com.kingdee.eas.port.pm.qa.IProjectOA.class);
    }
    
    public static com.kingdee.eas.port.pm.qa.IProjectOA getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IProjectOA)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FD2BEEC9") ,com.kingdee.eas.port.pm.qa.IProjectOA.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.qa.IProjectOA getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IProjectOA)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FD2BEEC9"));
    }
    public static com.kingdee.eas.port.pm.qa.IProjectOA getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.qa.IProjectOA)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FD2BEEC9"));
    }
}