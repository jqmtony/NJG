package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectIndexDataFactory
{
    private ProjectIndexDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IProjectIndexData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectIndexData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("734D0775") ,com.kingdee.eas.fdc.basedata.IProjectIndexData.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IProjectIndexData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectIndexData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("734D0775") ,com.kingdee.eas.fdc.basedata.IProjectIndexData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IProjectIndexData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectIndexData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("734D0775"));
    }
    public static com.kingdee.eas.fdc.basedata.IProjectIndexData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IProjectIndexData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("734D0775"));
    }
}