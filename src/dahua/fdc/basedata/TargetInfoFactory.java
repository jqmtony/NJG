package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TargetInfoFactory
{
    private TargetInfoFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ITargetInfo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetInfo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4825AA51") ,com.kingdee.eas.fdc.basedata.ITargetInfo.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ITargetInfo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetInfo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4825AA51") ,com.kingdee.eas.fdc.basedata.ITargetInfo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ITargetInfo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetInfo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4825AA51"));
    }
    public static com.kingdee.eas.fdc.basedata.ITargetInfo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetInfo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4825AA51"));
    }
}