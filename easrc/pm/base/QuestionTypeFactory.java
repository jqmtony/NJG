package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuestionTypeFactory
{
    private QuestionTypeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IQuestionType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IQuestionType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("41DA5301") ,com.kingdee.eas.port.pm.base.IQuestionType.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IQuestionType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IQuestionType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("41DA5301") ,com.kingdee.eas.port.pm.base.IQuestionType.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IQuestionType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IQuestionType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("41DA5301"));
    }
    public static com.kingdee.eas.port.pm.base.IQuestionType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IQuestionType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("41DA5301"));
    }
}