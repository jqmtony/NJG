package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExamineIndicatorsTreeFactory
{
    private ExamineIndicatorsTreeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IExamineIndicatorsTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IExamineIndicatorsTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A3A58222") ,com.kingdee.eas.port.pm.base.IExamineIndicatorsTree.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IExamineIndicatorsTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IExamineIndicatorsTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A3A58222") ,com.kingdee.eas.port.pm.base.IExamineIndicatorsTree.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IExamineIndicatorsTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IExamineIndicatorsTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A3A58222"));
    }
    public static com.kingdee.eas.port.pm.base.IExamineIndicatorsTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IExamineIndicatorsTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A3A58222"));
    }
}