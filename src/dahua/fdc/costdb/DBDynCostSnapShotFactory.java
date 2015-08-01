package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DBDynCostSnapShotFactory
{
    private DBDynCostSnapShotFactory()
    {
    }
    public static com.kingdee.eas.fdc.costdb.IDBDynCostSnapShot getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBDynCostSnapShot)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FB1EFB8A") ,com.kingdee.eas.fdc.costdb.IDBDynCostSnapShot.class);
    }
    
    public static com.kingdee.eas.fdc.costdb.IDBDynCostSnapShot getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBDynCostSnapShot)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FB1EFB8A") ,com.kingdee.eas.fdc.costdb.IDBDynCostSnapShot.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costdb.IDBDynCostSnapShot getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBDynCostSnapShot)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FB1EFB8A"));
    }
    public static com.kingdee.eas.fdc.costdb.IDBDynCostSnapShot getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBDynCostSnapShot)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FB1EFB8A"));
    }
}