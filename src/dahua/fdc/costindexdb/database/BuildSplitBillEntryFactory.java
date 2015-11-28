package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildSplitBillEntryFactory
{
    private BuildSplitBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("969D5B38") ,com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("969D5B38") ,com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("969D5B38"));
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildSplitBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("969D5B38"));
    }
}