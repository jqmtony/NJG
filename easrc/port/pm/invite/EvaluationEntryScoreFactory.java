package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationEntryScoreFactory
{
    private EvaluationEntryScoreFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryScore getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryScore)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A82FF065") ,com.kingdee.eas.port.pm.invite.IEvaluationEntryScore.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryScore getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryScore)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A82FF065") ,com.kingdee.eas.port.pm.invite.IEvaluationEntryScore.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryScore getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryScore)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A82FF065"));
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationEntryScore getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationEntryScore)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A82FF065"));
    }
}