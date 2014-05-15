package com.kingdee.eas.port.pm.evaluation;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationE1Factory
{
    private EvaluationE1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.evaluation.IEvaluationE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.evaluation.IEvaluationE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FAC1353E") ,com.kingdee.eas.port.pm.evaluation.IEvaluationE1.class);
    }
    
    public static com.kingdee.eas.port.pm.evaluation.IEvaluationE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.evaluation.IEvaluationE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FAC1353E") ,com.kingdee.eas.port.pm.evaluation.IEvaluationE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.evaluation.IEvaluationE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.evaluation.IEvaluationE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FAC1353E"));
    }
    public static com.kingdee.eas.port.pm.evaluation.IEvaluationE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.evaluation.IEvaluationE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FAC1353E"));
    }
}