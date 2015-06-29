package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichExamCheckTabFactory
{
    private RichExamCheckTabFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichExamCheckTab getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamCheckTab)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("26353EB6") ,com.kingdee.eas.custom.richinf.IRichExamCheckTab.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichExamCheckTab getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamCheckTab)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("26353EB6") ,com.kingdee.eas.custom.richinf.IRichExamCheckTab.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichExamCheckTab getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamCheckTab)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("26353EB6"));
    }
    public static com.kingdee.eas.custom.richinf.IRichExamCheckTab getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamCheckTab)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("26353EB6"));
    }
}