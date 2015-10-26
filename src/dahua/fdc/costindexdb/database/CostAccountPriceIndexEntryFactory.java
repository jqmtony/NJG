package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostAccountPriceIndexEntryFactory
{
    private CostAccountPriceIndexEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndexEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndexEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A670C290") ,com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndexEntry.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndexEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndexEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A670C290") ,com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndexEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndexEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndexEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A670C290"));
    }
    public static com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndexEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndexEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A670C290"));
    }
}