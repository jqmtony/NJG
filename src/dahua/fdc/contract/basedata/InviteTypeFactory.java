package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteTypeFactory
{
    private InviteTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IInviteType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IInviteType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("72931655") ,com.kingdee.eas.fdc.basedata.IInviteType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IInviteType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IInviteType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("72931655") ,com.kingdee.eas.fdc.basedata.IInviteType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IInviteType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IInviteType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("72931655"));
    }
    public static com.kingdee.eas.fdc.basedata.IInviteType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IInviteType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("72931655"));
    }
}