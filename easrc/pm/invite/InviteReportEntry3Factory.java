package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteReportEntry3Factory
{
    private InviteReportEntry3Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry3 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry3)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("76421707") ,com.kingdee.eas.port.pm.invite.IInviteReportEntry3.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry3 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry3)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("76421707") ,com.kingdee.eas.port.pm.invite.IInviteReportEntry3.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry3 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry3)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("76421707"));
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry3 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry3)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("76421707"));
    }
}