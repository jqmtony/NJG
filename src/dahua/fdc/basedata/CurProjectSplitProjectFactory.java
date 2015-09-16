package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CurProjectSplitProjectFactory
{
    private CurProjectSplitProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ICurProjectSplitProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjectSplitProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7FD7CB6A") ,com.kingdee.eas.fdc.basedata.ICurProjectSplitProject.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ICurProjectSplitProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjectSplitProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7FD7CB6A") ,com.kingdee.eas.fdc.basedata.ICurProjectSplitProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ICurProjectSplitProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjectSplitProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7FD7CB6A"));
    }
    public static com.kingdee.eas.fdc.basedata.ICurProjectSplitProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICurProjectSplitProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7FD7CB6A"));
    }
}