package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectStatusFactory
{
    private ProjectStatusFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IProjectStatus getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectStatus)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8D7AD2B9") ,com.kingdee.eas.fdc.basedata.IProjectStatus.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IProjectStatus getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectStatus)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8D7AD2B9") ,com.kingdee.eas.fdc.basedata.IProjectStatus.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IProjectStatus getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectStatus)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8D7AD2B9"));
    }
    public static com.kingdee.eas.fdc.basedata.IProjectStatus getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectStatus)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8D7AD2B9"));
    }
}