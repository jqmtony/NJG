package com.kingdee.eas.port.pm.invite;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationSumFactory
{
    private EvaluationSumFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationSum getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationSum)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("215D9AC6") ,com.kingdee.eas.port.pm.invite.IEvaluationSum.class);
    }
    
    public static com.kingdee.eas.port.pm.invite.IEvaluationSum getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationSum)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("215D9AC6") ,com.kingdee.eas.port.pm.invite.IEvaluationSum.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationSum getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationSum)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("215D9AC6"));
    }
    public static com.kingdee.eas.port.pm.invite.IEvaluationSum getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invite.IEvaluationSum)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("215D9AC6"));
    }
}