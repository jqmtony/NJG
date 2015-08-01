package com.kingdee.eas.fdc.basedata.scheme;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FdcEntityObjectFactory
{
    private FdcEntityObjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcEntityObject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcEntityObject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C1246036") ,com.kingdee.eas.fdc.basedata.scheme.IFdcEntityObject.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcEntityObject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcEntityObject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C1246036") ,com.kingdee.eas.fdc.basedata.scheme.IFdcEntityObject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcEntityObject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcEntityObject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C1246036"));
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcEntityObject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcEntityObject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C1246036"));
    }
}