package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupEvaluationIndicatorTreeFactory
{
    private SupEvaluationIndicatorTreeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluationIndicatorTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluationIndicatorTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("769DC344") ,com.kingdee.eas.port.pm.base.ISupEvaluationIndicatorTree.class);
    }
    
    public static com.kingdee.eas.port.pm.base.ISupEvaluationIndicatorTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluationIndicatorTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("769DC344") ,com.kingdee.eas.port.pm.base.ISupEvaluationIndicatorTree.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluationIndicatorTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluationIndicatorTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("769DC344"));
    }
    public static com.kingdee.eas.port.pm.base.ISupEvaluationIndicatorTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.ISupEvaluationIndicatorTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("769DC344"));
    }
}