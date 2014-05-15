package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvitePlanFactory
{
    private InvitePlanFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IInvitePlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInvitePlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("61F9E3DB") ,com.kingdee.eas.port.pm.invite.IInvitePlan.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IInvitePlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInvitePlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("61F9E3DB") ,com.kingdee.eas.port.pm.invite.IInvitePlan.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IInvitePlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInvitePlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("61F9E3DB"));
    }
    public static com.kingdee.eas.port.pm.invite.IInvitePlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInvitePlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("61F9E3DB"));
    }
}