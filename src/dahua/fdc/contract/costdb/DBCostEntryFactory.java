package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DBCostEntryFactory
{
    private DBCostEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.costdb.IDBCostEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBCostEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("22A4C645") ,com.kingdee.eas.fdc.costdb.IDBCostEntry.class);
    }
    
    public static com.kingdee.eas.fdc.costdb.IDBCostEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBCostEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("22A4C645") ,com.kingdee.eas.fdc.costdb.IDBCostEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costdb.IDBCostEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBCostEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("22A4C645"));
    }
    public static com.kingdee.eas.fdc.costdb.IDBCostEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBCostEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("22A4C645"));
    }
}