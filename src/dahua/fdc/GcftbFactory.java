package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GcftbFactory
{
    private GcftbFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IGcftb getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IGcftb)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F767F4D1") ,com.kingdee.eas.fdc.gcftbiaoa.IGcftb.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IGcftb getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IGcftb)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F767F4D1") ,com.kingdee.eas.fdc.gcftbiaoa.IGcftb.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IGcftb getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IGcftb)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F767F4D1"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IGcftb getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IGcftb)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F767F4D1"));
    }
}