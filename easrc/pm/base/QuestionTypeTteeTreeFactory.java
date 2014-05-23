package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuestionTypeTteeTreeFactory
{
    private QuestionTypeTteeTreeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IQuestionTypeTteeTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IQuestionTypeTteeTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4C61F8FF") ,com.kingdee.eas.port.pm.base.IQuestionTypeTteeTree.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IQuestionTypeTteeTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IQuestionTypeTteeTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4C61F8FF") ,com.kingdee.eas.port.pm.base.IQuestionTypeTteeTree.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IQuestionTypeTteeTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IQuestionTypeTteeTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4C61F8FF"));
    }
    public static com.kingdee.eas.port.pm.base.IQuestionTypeTteeTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IQuestionTypeTteeTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4C61F8FF"));
    }
}