package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuestionTypeTteeFactory
{
    private QuestionTypeTteeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IQuestionTypeTtee getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IQuestionTypeTtee)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("56AED9C1") ,com.kingdee.eas.port.pm.base.IQuestionTypeTtee.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IQuestionTypeTtee getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IQuestionTypeTtee)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("56AED9C1") ,com.kingdee.eas.port.pm.base.IQuestionTypeTtee.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IQuestionTypeTtee getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IQuestionTypeTtee)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("56AED9C1"));
    }
    public static com.kingdee.eas.port.pm.base.IQuestionTypeTtee getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IQuestionTypeTtee)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("56AED9C1"));
    }
}