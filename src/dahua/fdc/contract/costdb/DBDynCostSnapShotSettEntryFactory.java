package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DBDynCostSnapShotSettEntryFactory
{
    private DBDynCostSnapShotSettEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotSettEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotSettEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("286E2AD6") ,com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotSettEntry.class);
    }
    
    public static com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotSettEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotSettEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("286E2AD6") ,com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotSettEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotSettEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotSettEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("286E2AD6"));
    }
    public static com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotSettEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBDynCostSnapShotSettEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("286E2AD6"));
    }
}