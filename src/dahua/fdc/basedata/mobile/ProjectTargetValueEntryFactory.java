package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectTargetValueEntryFactory
{
    private ProjectTargetValueEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2D9A90D7") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2D9A90D7") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2D9A90D7"));
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetValueEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2D9A90D7"));
    }
}