package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichCustomWriteOffE2Factory
{
    private RichCustomWriteOffE2Factory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffE2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffE2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6FDD26F8") ,com.kingdee.eas.custom.richinf.IRichCustomWriteOffE2.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffE2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffE2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6FDD26F8") ,com.kingdee.eas.custom.richinf.IRichCustomWriteOffE2.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffE2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffE2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6FDD26F8"));
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOffE2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOffE2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6FDD26F8"));
    }
}