package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichCustomWriteOffFpEntryFactory
{
    private RichCustomWriteOffFpEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffFpEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffFpEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("144F3E1D") ,com.kingdee.eas.custom.richinf.IRichCustomWriteOffFpEntry.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffFpEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffFpEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("144F3E1D") ,com.kingdee.eas.custom.richinf.IRichCustomWriteOffFpEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffFpEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffFpEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("144F3E1D"));
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffFpEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffFpEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("144F3E1D"));
    }
}