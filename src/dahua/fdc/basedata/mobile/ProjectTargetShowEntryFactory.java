package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectTargetShowEntryFactory
{
    private ProjectTargetShowEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E58A055F") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E58A055F") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E58A055F"));
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E58A055F"));
    }
}