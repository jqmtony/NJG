package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichCustomWriteOffEntryFactory
{
    private RichCustomWriteOffEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B8D6DE07") ,com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntry.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B8D6DE07") ,com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B8D6DE07"));
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B8D6DE07"));
    }
}