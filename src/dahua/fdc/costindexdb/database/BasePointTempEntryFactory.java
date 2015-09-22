package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BasePointTempEntryFactory
{
    private BasePointTempEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBasePointTempEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBasePointTempEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FE15A4E6") ,com.kingdee.eas.fdc.costindexdb.database.IBasePointTempEntry.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.database.IBasePointTempEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBasePointTempEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FE15A4E6") ,com.kingdee.eas.fdc.costindexdb.database.IBasePointTempEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBasePointTempEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBasePointTempEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FE15A4E6"));
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBasePointTempEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBasePointTempEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FE15A4E6"));
    }
}