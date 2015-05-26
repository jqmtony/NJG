package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichCompayWriteOffE2Factory
{
    private RichCompayWriteOffE2Factory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOffE2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOffE2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DE7B2B8E") ,com.kingdee.eas.custom.richinf.IRichCompayWriteOffE2.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOffE2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOffE2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DE7B2B8E") ,com.kingdee.eas.custom.richinf.IRichCompayWriteOffE2.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOffE2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOffE2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DE7B2B8E"));
    }
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOffE2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOffE2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DE7B2B8E"));
    }
}