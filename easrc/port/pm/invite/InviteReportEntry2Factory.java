package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteReportEntry2Factory
{
    private InviteReportEntry2Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("76421706") ,com.kingdee.eas.port.pm.invite.IInviteReportEntry2.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("76421706") ,com.kingdee.eas.port.pm.invite.IInviteReportEntry2.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("76421706"));
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("76421706"));
    }
}