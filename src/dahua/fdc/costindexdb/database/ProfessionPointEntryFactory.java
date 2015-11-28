package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProfessionPointEntryFactory
{
    private ProfessionPointEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IProfessionPointEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IProfessionPointEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("33ECDF85") ,com.kingdee.eas.fdc.costindexdb.database.IProfessionPointEntry.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.database.IProfessionPointEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IProfessionPointEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("33ECDF85") ,com.kingdee.eas.fdc.costindexdb.database.IProfessionPointEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IProfessionPointEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IProfessionPointEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("33ECDF85"));
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IProfessionPointEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IProfessionPointEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("33ECDF85"));
    }
}