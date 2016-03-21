package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IndoorengFactory
{
    private IndoorengFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndooreng getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndooreng)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("939CCB04") ,com.kingdee.eas.fdc.gcftbiaoa.IIndooreng.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndooreng getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndooreng)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("939CCB04") ,com.kingdee.eas.fdc.gcftbiaoa.IIndooreng.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndooreng getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndooreng)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("939CCB04"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IIndooreng getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IIndooreng)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("939CCB04"));
    }
}