package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationEntryValidFactory
{
    private EvaluationEntryValidFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryValid getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryValid)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A85941CF") ,com.kingdee.eas.port.pm.invite.IEvaluationEntryValid.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryValid getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryValid)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A85941CF") ,com.kingdee.eas.port.pm.invite.IEvaluationEntryValid.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryValid getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryValid)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A85941CF"));
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryValid getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryValid)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A85941CF"));
    }
}