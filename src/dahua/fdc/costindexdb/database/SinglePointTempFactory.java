package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SinglePointTempFactory
{
    private SinglePointTempFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.database.ISinglePointTemp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ISinglePointTemp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("66F13515") ,com.kingdee.eas.fdc.costindexdb.database.ISinglePointTemp.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.database.ISinglePointTemp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ISinglePointTemp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("66F13515") ,com.kingdee.eas.fdc.costindexdb.database.ISinglePointTemp.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.database.ISinglePointTemp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ISinglePointTemp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("66F13515"));
    }
    public static com.kingdee.eas.fdc.costindexdb.database.ISinglePointTemp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.ISinglePointTemp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("66F13515"));
    }
}