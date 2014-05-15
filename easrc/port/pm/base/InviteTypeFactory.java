package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteTypeFactory
{
    private InviteTypeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IInviteType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IInviteType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C606B8E4") ,com.kingdee.eas.port.pm.base.IInviteType.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IInviteType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IInviteType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C606B8E4") ,com.kingdee.eas.port.pm.base.IInviteType.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IInviteType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IInviteType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C606B8E4"));
    }
    public static com.kingdee.eas.port.pm.base.IInviteType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IInviteType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C606B8E4"));
    }
}