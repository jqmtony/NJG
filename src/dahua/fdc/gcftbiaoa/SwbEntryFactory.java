package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SwbEntryFactory
{
    private SwbEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.ISwbEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.ISwbEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E1AACC1B") ,com.kingdee.eas.fdc.gcftbiaoa.ISwbEntry.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.ISwbEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.ISwbEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E1AACC1B") ,com.kingdee.eas.fdc.gcftbiaoa.ISwbEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.ISwbEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.ISwbEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E1AACC1B"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.ISwbEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.ISwbEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E1AACC1B"));
    }
}