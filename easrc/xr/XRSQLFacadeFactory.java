package com.kingdee.eas.xr;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class XRSQLFacadeFactory
{
    private XRSQLFacadeFactory()
    {
    }
    public static com.kingdee.eas.xr.IXRSQLFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.xr.IXRSQLFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F29FFEF2") ,com.kingdee.eas.xr.IXRSQLFacade.class);
    }
    
    public static com.kingdee.eas.xr.IXRSQLFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.xr.IXRSQLFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F29FFEF2") ,com.kingdee.eas.xr.IXRSQLFacade.class, objectCtx);
    }
    public static com.kingdee.eas.xr.IXRSQLFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.xr.IXRSQLFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F29FFEF2"));
    }
    public static com.kingdee.eas.xr.IXRSQLFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.xr.IXRSQLFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F29FFEF2"));
    }
}