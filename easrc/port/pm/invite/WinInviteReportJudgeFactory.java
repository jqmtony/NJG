package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WinInviteReportJudgeFactory
{
    private WinInviteReportJudgeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IWinInviteReportJudge getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReportJudge)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3E9385C7") ,com.kingdee.eas.port.pm.invite.IWinInviteReportJudge.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IWinInviteReportJudge getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReportJudge)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3E9385C7") ,com.kingdee.eas.port.pm.invite.IWinInviteReportJudge.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IWinInviteReportJudge getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReportJudge)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3E9385C7"));
    }
    public static com.kingdee.eas.port.pm.invite.IWinInviteReportJudge getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReportJudge)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3E9385C7"));
    }
}