package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TargetWarningFactory
{
    private TargetWarningFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ITargetWarning getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetWarning)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A6396319") ,com.kingdee.eas.fdc.basedata.ITargetWarning.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ITargetWarning getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetWarning)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A6396319") ,com.kingdee.eas.fdc.basedata.ITargetWarning.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ITargetWarning getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetWarning)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A6396319"));
    }
    public static com.kingdee.eas.fdc.basedata.ITargetWarning getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetWarning)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A6396319"));
    }
}