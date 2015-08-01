package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DBDynCostSnapShotProTypEntryFactory
{
    private DBDynCostSnapShotProTypEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotProTypEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotProTypEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4CB5408A") ,com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotProTypEntry.class);
    }
    
    public static com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotProTypEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotProTypEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4CB5408A") ,com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotProTypEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotProTypEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotProTypEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4CB5408A"));
    }
    public static com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotProTypEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotProTypEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4CB5408A"));
    }
}