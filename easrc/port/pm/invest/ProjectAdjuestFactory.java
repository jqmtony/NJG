package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectAdjuestFactory
{
    private ProjectAdjuestFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IProjectAdjuest getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectAdjuest)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3F94B9DC") ,com.kingdee.eas.port.pm.invest.IProjectAdjuest.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IProjectAdjuest getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectAdjuest)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3F94B9DC") ,com.kingdee.eas.port.pm.invest.IProjectAdjuest.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IProjectAdjuest getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectAdjuest)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3F94B9DC"));
    }
    public static com.kingdee.eas.port.pm.invest.IProjectAdjuest getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IProjectAdjuest)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3F94B9DC"));
    }
}