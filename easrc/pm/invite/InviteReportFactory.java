package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteReportFactory
{
    private InviteReportFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReport getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReport)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CE1E4966") ,com.kingdee.eas.port.pm.invite.IInviteReport.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IInviteReport getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReport)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CE1E4966") ,com.kingdee.eas.port.pm.invite.IInviteReport.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReport getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReport)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CE1E4966"));
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReport getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReport)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CE1E4966"));
    }
}