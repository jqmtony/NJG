package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupEvaluateTemplateEntryFactory
{
    private SupEvaluateTemplateEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluateTemplateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluateTemplateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("81F81792") ,com.kingdee.eas.port.pm.base.ISupEvaluateTemplateEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.base.ISupEvaluateTemplateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluateTemplateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("81F81792") ,com.kingdee.eas.port.pm.base.ISupEvaluateTemplateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluateTemplateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluateTemplateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("81F81792"));
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluateTemplateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluateTemplateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("81F81792"));
    }
}