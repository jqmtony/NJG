package com.kingdee.eas.custom.richbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExamTypeFactory
{
    private ExamTypeFactory()
    {
    }
    public static com.kingdee.eas.custom.richbase.IExamType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IExamType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B021D2EF") ,com.kingdee.eas.custom.richbase.IExamType.class);
    }
    
    public static com.kingdee.eas.custom.richbase.IExamType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IExamType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B021D2EF") ,com.kingdee.eas.custom.richbase.IExamType.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richbase.IExamType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IExamType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B021D2EF"));
    }
    public static com.kingdee.eas.custom.richbase.IExamType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richbase.IExamType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B021D2EF"));
    }
}