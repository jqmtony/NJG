package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichCustomWriteOffEntryFpEntryFactory
{
    private RichCustomWriteOffEntryFpEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntryFpEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntryFpEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("208EADC1") ,com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntryFpEntry.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntryFpEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntryFpEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("208EADC1") ,com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntryFpEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntryFpEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntryFpEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("208EADC1"));
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntryFpEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffEntryFpEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("208EADC1"));
    }
}