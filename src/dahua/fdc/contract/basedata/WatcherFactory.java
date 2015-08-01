package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class WatcherFactory
{
    private WatcherFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IWatcher getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IWatcher)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("11B86E8A") ,com.kingdee.eas.fdc.basedata.IWatcher.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IWatcher getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IWatcher)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("11B86E8A") ,com.kingdee.eas.fdc.basedata.IWatcher.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IWatcher getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IWatcher)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("11B86E8A"));
    }
    public static com.kingdee.eas.fdc.basedata.IWatcher getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IWatcher)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("11B86E8A"));
    }
}