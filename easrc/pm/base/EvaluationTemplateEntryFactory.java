package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationTemplateEntryFactory
{
    private EvaluationTemplateEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationTemplateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationTemplateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("71D148DB") ,com.kingdee.eas.port.pm.base.IEvaluationTemplateEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IEvaluationTemplateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationTemplateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("71D148DB") ,com.kingdee.eas.port.pm.base.IEvaluationTemplateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationTemplateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationTemplateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("71D148DB"));
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationTemplateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationTemplateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("71D148DB"));
    }
}