package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationEntryTotalFactory
{
    private EvaluationEntryTotalFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryTotal getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryTotal)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A8438D17") ,com.kingdee.eas.port.pm.invite.IEvaluationEntryTotal.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryTotal getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryTotal)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A8438D17") ,com.kingdee.eas.port.pm.invite.IEvaluationEntryTotal.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryTotal getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryTotal)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A8438D17"));
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryTotal getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryTotal)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A8438D17"));
    }
}