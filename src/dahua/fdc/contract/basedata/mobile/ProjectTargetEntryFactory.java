package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectTargetEntryFactory
{
    private ProjectTargetEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B1B9FF5C") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B1B9FF5C") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B1B9FF5C"));
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B1B9FF5C"));
    }
}