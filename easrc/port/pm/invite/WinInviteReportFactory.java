package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WinInviteReportFactory
{
    private WinInviteReportFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IWinInviteReport getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReport)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F8A0F730") ,com.kingdee.eas.port.pm.invite.IWinInviteReport.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IWinInviteReport getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReport)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F8A0F730") ,com.kingdee.eas.port.pm.invite.IWinInviteReport.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IWinInviteReport getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReport)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F8A0F730"));
    }
    public static com.kingdee.eas.port.pm.invite.IWinInviteReport getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReport)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F8A0F730"));
    }
}