package com.kingdee.eas.test;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurOrderEntryFactory
{
    private PurOrderEntryFactory()
    {
    }
    public static com.kingdee.eas.test.IPurOrderEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.test.IPurOrderEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B671483A") ,com.kingdee.eas.test.IPurOrderEntry.class);
    }
    
    public static com.kingdee.eas.test.IPurOrderEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.test.IPurOrderEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B671483A") ,com.kingdee.eas.test.IPurOrderEntry.class, objectCtx);
    }
    public static com.kingdee.eas.test.IPurOrderEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.test.IPurOrderEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B671483A"));
    }
    public static com.kingdee.eas.test.IPurOrderEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.test.IPurOrderEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B671483A"));
    }
}