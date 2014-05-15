package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExamineIndicatorsFactory
{
    private ExamineIndicatorsFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IExamineIndicators getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IExamineIndicators)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("85126C64") ,com.kingdee.eas.port.pm.base.IExamineIndicators.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IExamineIndicators getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IExamineIndicators)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("85126C64") ,com.kingdee.eas.port.pm.base.IExamineIndicators.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IExamineIndicators getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IExamineIndicators)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("85126C64"));
    }
    public static com.kingdee.eas.port.pm.base.IExamineIndicators getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IExamineIndicators)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("85126C64"));
    }
}