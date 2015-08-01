package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FdcMaterialFactory
{
    private FdcMaterialFactory()
    {
    }
    public static com.kingdee.eas.fdc.costdb.IFdcMaterial getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IFdcMaterial)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C469880A") ,com.kingdee.eas.fdc.costdb.IFdcMaterial.class);
    }
    
    public static com.kingdee.eas.fdc.costdb.IFdcMaterial getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IFdcMaterial)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C469880A") ,com.kingdee.eas.fdc.costdb.IFdcMaterial.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costdb.IFdcMaterial getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IFdcMaterial)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C469880A"));
    }
    public static com.kingdee.eas.fdc.costdb.IFdcMaterial getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IFdcMaterial)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C469880A"));
    }
}