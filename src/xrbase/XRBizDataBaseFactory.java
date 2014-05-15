package com.kingdee.eas.xr.xrbase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class XRBizDataBaseFactory
{
    private XRBizDataBaseFactory()
    {
    }
    public static com.kingdee.eas.xr.xrbase.IXRBizDataBase getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.xr.xrbase.IXRBizDataBase)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F4FAD313") ,com.kingdee.eas.xr.xrbase.IXRBizDataBase.class);
    }
    
    public static com.kingdee.eas.xr.xrbase.IXRBizDataBase getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.xr.xrbase.IXRBizDataBase)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F4FAD313") ,com.kingdee.eas.xr.xrbase.IXRBizDataBase.class, objectCtx);
    }
    public static com.kingdee.eas.xr.xrbase.IXRBizDataBase getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.xr.xrbase.IXRBizDataBase)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F4FAD313"));
    }
    public static com.kingdee.eas.xr.xrbase.IXRBizDataBase getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.xr.xrbase.IXRBizDataBase)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F4FAD313"));
    }
}