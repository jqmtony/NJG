package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IndoorengEntryFactory
{
    private IndoorengEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndoorengEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndoorengEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C07D8FCE") ,com.kingdee.eas.fdc.gcftbiaoa.IIndoorengEntry.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndoorengEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndoorengEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C07D8FCE") ,com.kingdee.eas.fdc.gcftbiaoa.IIndoorengEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndoorengEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndoorengEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C07D8FCE"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndoorengEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndoorengEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C07D8FCE"));
    }
}