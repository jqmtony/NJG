package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteReportEntry4Factory
{
    private InviteReportEntry4Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry4 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry4)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("76421708") ,com.kingdee.eas.port.pm.invite.IInviteReportEntry4.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry4 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry4)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("76421708") ,com.kingdee.eas.port.pm.invite.IInviteReportEntry4.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry4 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry4)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("76421708"));
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportEntry4 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportEntry4)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("76421708"));
    }
}