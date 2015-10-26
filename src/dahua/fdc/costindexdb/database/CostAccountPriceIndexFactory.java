package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostAccountPriceIndexFactory
{
    private CostAccountPriceIndexFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndex getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndex)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F4C16702") ,com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndex.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndex getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndex)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F4C16702") ,com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndex.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndex getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndex)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F4C16702"));
    }
    public static com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndex getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ICostAccountPriceIndex)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F4C16702"));
    }
}