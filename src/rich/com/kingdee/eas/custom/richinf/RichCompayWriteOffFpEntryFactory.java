package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichCompayWriteOffFpEntryFactory
{
    private RichCompayWriteOffFpEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOffFpEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOffFpEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EBCAA747") ,com.kingdee.eas.custom.richinf.IRichCompayWriteOffFpEntry.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOffFpEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOffFpEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EBCAA747") ,com.kingdee.eas.custom.richinf.IRichCompayWriteOffFpEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOffFpEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOffFpEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EBCAA747"));
    }
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOffFpEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOffFpEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EBCAA747"));
    }
}