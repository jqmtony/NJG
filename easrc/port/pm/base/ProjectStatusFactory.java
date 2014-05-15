package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectStatusFactory
{
    private ProjectStatusFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IProjectStatus getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IProjectStatus)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EB0CF1CA") ,com.kingdee.eas.port.pm.base.IProjectStatus.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IProjectStatus getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IProjectStatus)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EB0CF1CA") ,com.kingdee.eas.port.pm.base.IProjectStatus.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IProjectStatus getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IProjectStatus)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EB0CF1CA"));
    }
    public static com.kingdee.eas.port.pm.base.IProjectStatus getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IProjectStatus)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EB0CF1CA"));
    }
}