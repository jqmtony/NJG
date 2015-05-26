package com.kingdee.eas.test;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurOrderFactory
{
    private PurOrderFactory()
    {
    }
    public static com.kingdee.eas.test.IPurOrder getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.test.IPurOrder)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("823A7F18") ,com.kingdee.eas.test.IPurOrder.class);
    }
    
    public static com.kingdee.eas.test.IPurOrder getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.test.IPurOrder)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("823A7F18") ,com.kingdee.eas.test.IPurOrder.class, objectCtx);
    }
    public static com.kingdee.eas.test.IPurOrder getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.test.IPurOrder)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("823A7F18"));
    }
    public static com.kingdee.eas.test.IPurOrder getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.test.IPurOrder)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("823A7F18"));
    }
}