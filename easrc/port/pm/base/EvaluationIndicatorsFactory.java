package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationIndicatorsFactory
{
    private EvaluationIndicatorsFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationIndicators getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationIndicators)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("68C13261") ,com.kingdee.eas.port.pm.base.IEvaluationIndicators.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IEvaluationIndicators getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationIndicators)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("68C13261") ,com.kingdee.eas.port.pm.base.IEvaluationIndicators.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationIndicators getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationIndicators)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("68C13261"));
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationIndicators getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationIndicators)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("68C13261"));
    }
}