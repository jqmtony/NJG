package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WinInviteReportBudgetEntryFactory
{
    private WinInviteReportBudgetEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IWinInviteReportBudgetEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReportBudgetEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A2E07EDD") ,com.kingdee.eas.port.pm.invite.IWinInviteReportBudgetEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IWinInviteReportBudgetEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReportBudgetEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A2E07EDD") ,com.kingdee.eas.port.pm.invite.IWinInviteReportBudgetEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IWinInviteReportBudgetEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReportBudgetEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A2E07EDD"));
    }
    public static com.kingdee.eas.port.pm.invite.IWinInviteReportBudgetEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IWinInviteReportBudgetEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A2E07EDD"));
    }
}