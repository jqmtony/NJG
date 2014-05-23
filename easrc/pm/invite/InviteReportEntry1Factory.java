package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteReportEntry1Factory
{
    private InviteReportEntry1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("76421705") ,com.kingdee.eas.port.pm.invite.IInviteReportEntry1.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("76421705") ,com.kingdee.eas.port.pm.invite.IInviteReportEntry1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("76421705"));
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("76421705"));
    }
}