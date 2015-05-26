package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichCustomWriteOffFactory
{
    private RichCustomWriteOffFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOff getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOff)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C6D7BD2B") ,com.kingdee.eas.custom.richinf.IRichCustomWriteOff.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOff getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOff)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C6D7BD2B") ,com.kingdee.eas.custom.richinf.IRichCustomWriteOff.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOff getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOff)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C6D7BD2B"));
    }
    public static com.kingdee.eas.custom.richinf.IRichCustomWriteOff getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCustomWriteOff)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C6D7BD2B"));
    }
}