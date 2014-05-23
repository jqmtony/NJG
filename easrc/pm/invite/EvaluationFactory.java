package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationFactory
{
    private EvaluationFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4DBE6945") ,com.kingdee.eas.port.pm.invite.IEvaluation.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IEvaluation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4DBE6945") ,com.kingdee.eas.port.pm.invite.IEvaluation.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4DBE6945"));
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4DBE6945"));
    }
}