package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichCustomWriteOffDjEntryFactory
{
    private RichCustomWriteOffDjEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffDjEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffDjEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A045B3E1") ,com.kingdee.eas.custom.richinf.IRichCustomWriteOffDjEntry.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffDjEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffDjEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A045B3E1") ,com.kingdee.eas.custom.richinf.IRichCustomWriteOffDjEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffDjEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffDjEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A045B3E1"));
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffDjEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffDjEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A045B3E1"));
    }
}