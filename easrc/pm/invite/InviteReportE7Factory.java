package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteReportE7Factory
{
    private InviteReportE7Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportE7 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportE7)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BFB19078") ,com.kingdee.eas.port.pm.invite.IInviteReportE7.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IInviteReportE7 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportE7)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BFB19078") ,com.kingdee.eas.port.pm.invite.IInviteReportE7.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportE7 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportE7)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BFB19078"));
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportE7 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportE7)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BFB19078"));
    }
}