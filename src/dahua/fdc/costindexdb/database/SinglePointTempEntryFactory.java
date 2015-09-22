package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SinglePointTempEntryFactory
{
    private SinglePointTempEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.database.ISinglePointTempEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ISinglePointTempEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E05DC85D") ,com.kingdee.eas.fdc.costindexdb.database.ISinglePointTempEntry.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.database.ISinglePointTempEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ISinglePointTempEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E05DC85D") ,com.kingdee.eas.fdc.costindexdb.database.ISinglePointTempEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.database.ISinglePointTempEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ISinglePointTempEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E05DC85D"));
    }
    public static com.kingdee.eas.fdc.costindexdb.database.ISinglePointTempEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ISinglePointTempEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E05DC85D"));
    }
}