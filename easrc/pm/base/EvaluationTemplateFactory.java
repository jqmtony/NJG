package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationTemplateFactory
{
    private EvaluationTemplateFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6352DDD7") ,com.kingdee.eas.port.pm.base.IEvaluationTemplate.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IEvaluationTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6352DDD7") ,com.kingdee.eas.port.pm.base.IEvaluationTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6352DDD7"));
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6352DDD7"));
    }
}