package com.kingdee.eas.port.pm.evaluation;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationFactory
{
    private EvaluationFactory()
    {
    }
    public static com.kingdee.eas.port.pm.evaluation.IEvaluation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.evaluation.IEvaluation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E228B132") ,com.kingdee.eas.port.pm.evaluation.IEvaluation.class);
    }
    
    public static com.kingdee.eas.port.pm.evaluation.IEvaluation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.evaluation.IEvaluation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E228B132") ,com.kingdee.eas.port.pm.evaluation.IEvaluation.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.evaluation.IEvaluation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.evaluation.IEvaluation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E228B132"));
    }
    public static com.kingdee.eas.port.pm.evaluation.IEvaluation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.evaluation.IEvaluation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E228B132"));
    }
}