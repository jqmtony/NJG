package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WinInviteReportUnitFactory
{
    private WinInviteReportUnitFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IWinInviteReportUnit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReportUnit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("441A2CD4") ,com.kingdee.eas.port.pm.invite.IWinInviteReportUnit.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IWinInviteReportUnit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReportUnit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("441A2CD4") ,com.kingdee.eas.port.pm.invite.IWinInviteReportUnit.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IWinInviteReportUnit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReportUnit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("441A2CD4"));
    }
    public static com.kingdee.eas.port.pm.invite.IWinInviteReportUnit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReportUnit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("441A2CD4"));
    }
}