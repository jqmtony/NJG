package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupEvaluateTemplateFactory
{
    private SupEvaluateTemplateFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluateTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluateTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FC5578C0") ,com.kingdee.eas.port.pm.base.ISupEvaluateTemplate.class);
    }
    
    public static com.kingdee.eas.port.pm.base.ISupEvaluateTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluateTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FC5578C0") ,com.kingdee.eas.port.pm.base.ISupEvaluateTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluateTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluateTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FC5578C0"));
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluateTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluateTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FC5578C0"));
    }
}