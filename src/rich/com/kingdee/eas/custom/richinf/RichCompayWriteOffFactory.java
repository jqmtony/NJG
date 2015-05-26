package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichCompayWriteOffFactory
{
    private RichCompayWriteOffFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOff getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOff)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4539EF41") ,com.kingdee.eas.custom.richinf.IRichCompayWriteOff.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOff getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOff)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4539EF41") ,com.kingdee.eas.custom.richinf.IRichCompayWriteOff.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOff getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOff)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4539EF41"));
    }
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOff getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOff)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4539EF41"));
    }
}