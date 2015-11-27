package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GcftbEntryDetailFactory
{
    private GcftbEntryDetailFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntryDetail getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntryDetail)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D0FBA5D2") ,com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntryDetail.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntryDetail getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntryDetail)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D0FBA5D2") ,com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntryDetail.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntryDetail getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntryDetail)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D0FBA5D2"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntryDetail getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IGcftbEntryDetail)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D0FBA5D2"));
    }
}