package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SwbFactory
{
    private SwbFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.ISwb getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.ISwb)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5FE45297") ,com.kingdee.eas.fdc.gcftbiaoa.ISwb.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.ISwb getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.ISwb)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5FE45297") ,com.kingdee.eas.fdc.gcftbiaoa.ISwb.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.ISwb getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.ISwb)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5FE45297"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.ISwb getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.ISwb)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5FE45297"));
    }
}