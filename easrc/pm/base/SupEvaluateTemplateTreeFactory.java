package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupEvaluateTemplateTreeFactory
{
    private SupEvaluateTemplateTreeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluateTemplateTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluateTemplateTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6F93007E") ,com.kingdee.eas.port.pm.base.ISupEvaluateTemplateTree.class);
    }
    
    public static com.kingdee.eas.port.pm.base.ISupEvaluateTemplateTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluateTemplateTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6F93007E") ,com.kingdee.eas.port.pm.base.ISupEvaluateTemplateTree.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluateTemplateTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluateTemplateTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6F93007E"));
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluateTemplateTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluateTemplateTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6F93007E"));
    }
}