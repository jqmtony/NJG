package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildPriceIndexEntryFactory
{
    private BuildPriceIndexEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("96D234C1") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEntry.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("96D234C1") ,com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("96D234C1"));
    }
    public static com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBuildPriceIndexEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("96D234C1"));
    }
}