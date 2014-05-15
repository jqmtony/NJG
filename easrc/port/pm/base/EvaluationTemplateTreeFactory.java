package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EvaluationTemplateTreeFactory
{
    private EvaluationTemplateTreeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationTemplateTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationTemplateTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A09A0215") ,com.kingdee.eas.port.pm.base.IEvaluationTemplateTree.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IEvaluationTemplateTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationTemplateTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A09A0215") ,com.kingdee.eas.port.pm.base.IEvaluationTemplateTree.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationTemplateTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationTemplateTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A09A0215"));
    }
    public static com.kingdee.eas.port.pm.base.IEvaluationTemplateTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IEvaluationTemplateTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A09A0215"));
    }
}