package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteReportEntry5Factory
{
    private InviteReportEntry5Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry5 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry5)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("76421709") ,com.kingdee.eas.port.pm.invite.IInviteReportEntry5.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry5 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry5)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("76421709") ,com.kingdee.eas.port.pm.invite.IInviteReportEntry5.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry5 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry5)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("76421709"));
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry5 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry5)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("76421709"));
    }
}