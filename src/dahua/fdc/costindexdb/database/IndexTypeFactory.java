package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class IndexTypeFactory
{
    private IndexTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IIndexType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IIndexType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1D50E4E5") ,com.kingdee.eas.fdc.costindexdb.database.IIndexType.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.database.IIndexType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IIndexType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1D50E4E5") ,com.kingdee.eas.fdc.costindexdb.database.IIndexType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IIndexType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IIndexType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1D50E4E5"));
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IIndexType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IIndexType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1D50E4E5"));
    }
}