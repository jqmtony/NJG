package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InviteReportE6Factory
{
    private InviteReportE6Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportE6 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportE6)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BFB19077") ,com.kingdee.eas.port.pm.invite.IInviteReportE6.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IInviteReportE6 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportE6)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BFB19077") ,com.kingdee.eas.port.pm.invite.IInviteReportE6.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportE6 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportE6)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BFB19077"));
    }
    public static com.kingdee.eas.port.pm.invite.IInviteReportE6 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IInviteReportE6)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BFB19077"));
    }
}