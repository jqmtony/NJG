package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildSplitBillEntryDetailFactory
{
    private BuildSplitBillEntryDetailFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntryDetail getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntryDetail)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("501915A9") ,com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntryDetail.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntryDetail getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntryDetail)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("501915A9") ,com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntryDetail.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntryDetail getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntryDetail)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("501915A9"));
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntryDetail getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntryDetail)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("501915A9"));
    }
}