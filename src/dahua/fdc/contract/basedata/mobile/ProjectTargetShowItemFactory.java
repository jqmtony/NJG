package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProjectTargetShowItemFactory
{
    private ProjectTargetShowItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A450AC86") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowItem.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A450AC86") ,com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A450AC86"));
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IProjectTargetShowItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A450AC86"));
    }
}