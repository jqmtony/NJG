package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GcftbEntryFactory
{
    private GcftbEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("58157E21") ,com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntry.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("58157E21") ,com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("58157E21"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("58157E21"));
    }
}