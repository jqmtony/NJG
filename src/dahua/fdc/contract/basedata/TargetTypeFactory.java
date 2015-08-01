package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TargetTypeFactory
{
    private TargetTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ITargetType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("482AD4DD") ,com.kingdee.eas.fdc.basedata.ITargetType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ITargetType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("482AD4DD") ,com.kingdee.eas.fdc.basedata.ITargetType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ITargetType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("482AD4DD"));
    }
    public static com.kingdee.eas.fdc.basedata.ITargetType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("482AD4DD"));
    }
}