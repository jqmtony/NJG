package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichExamTempTabFactory
{
    private RichExamTempTabFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichExamTempTab getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamTempTab)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("90615CB8") ,com.kingdee.eas.custom.richinf.IRichExamTempTab.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichExamTempTab getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamTempTab)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("90615CB8") ,com.kingdee.eas.custom.richinf.IRichExamTempTab.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichExamTempTab getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamTempTab)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("90615CB8"));
    }
    public static com.kingdee.eas.custom.richinf.IRichExamTempTab getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamTempTab)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("90615CB8"));
    }
}