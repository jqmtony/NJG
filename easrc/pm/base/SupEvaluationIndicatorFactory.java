package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupEvaluationIndicatorFactory
{
    private SupEvaluationIndicatorFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluationIndicator getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluationIndicator)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("840C8E86") ,com.kingdee.eas.port.pm.base.ISupEvaluationIndicator.class);
    }
    
    public static com.kingdee.eas.port.pm.base.ISupEvaluationIndicator getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluationIndicator)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("840C8E86") ,com.kingdee.eas.port.pm.base.ISupEvaluationIndicator.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluationIndicator getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluationIndicator)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("840C8E86"));
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluationIndicator getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluationIndicator)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("840C8E86"));
    }
}