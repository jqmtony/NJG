package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectTypeFactory
{
    private ProjectTypeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IProjectType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IProjectType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1D044712") ,com.kingdee.eas.port.pm.base.IProjectType.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IProjectType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IProjectType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1D044712") ,com.kingdee.eas.port.pm.base.IProjectType.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IProjectType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IProjectType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1D044712"));
    }
    public static com.kingdee.eas.port.pm.base.IProjectType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IProjectType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1D044712"));
    }
}