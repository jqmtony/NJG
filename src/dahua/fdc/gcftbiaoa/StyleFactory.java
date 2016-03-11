package com.kingdee.eas.fdc.gcftbiaoa;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class StyleFactory
{
    private StyleFactory()
    {
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IStyle getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IStyle)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F8190F8A") ,com.kingdee.eas.fdc.gcftbiaoa.IStyle.class);
    }
    
    public static com.kingdee.eas.fdc.gcftbiaoa.IStyle getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IStyle)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F8190F8A") ,com.kingdee.eas.fdc.gcftbiaoa.IStyle.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IStyle getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IStyle)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F8190F8A"));
    }
    public static com.kingdee.eas.fdc.gcftbiaoa.IStyle getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.gcftbiaoa.IStyle)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F8190F8A"));
    }
}