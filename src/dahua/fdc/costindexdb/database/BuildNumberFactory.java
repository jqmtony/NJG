package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BuildNumberFactory
{
    private BuildNumberFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildNumber getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildNumber)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("38776AD0") ,com.kingdee.eas.fdc.costindexdb.database.IBuildNumber.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildNumber getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildNumber)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("38776AD0") ,com.kingdee.eas.fdc.costindexdb.database.IBuildNumber.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildNumber getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildNumber)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("38776AD0"));
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBuildNumber getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBuildNumber)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("38776AD0"));
    }
}