package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationIndicatorsTreeFactory
{
    private EvaluationIndicatorsTreeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationIndicatorsTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationIndicatorsTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E657019F") ,com.kingdee.eas.port.pm.base.IEvaluationIndicatorsTree.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IEvaluationIndicatorsTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationIndicatorsTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E657019F") ,com.kingdee.eas.port.pm.base.IEvaluationIndicatorsTree.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationIndicatorsTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationIndicatorsTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E657019F"));
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationIndicatorsTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationIndicatorsTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E657019F"));
    }
}