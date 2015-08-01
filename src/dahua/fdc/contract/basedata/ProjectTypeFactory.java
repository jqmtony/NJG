package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectTypeFactory
{
    private ProjectTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IProjectType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("020397C1") ,com.kingdee.eas.fdc.basedata.IProjectType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IProjectType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("020397C1") ,com.kingdee.eas.fdc.basedata.IProjectType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IProjectType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("020397C1"));
    }
    public static com.kingdee.eas.fdc.basedata.IProjectType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("020397C1"));
    }
}