package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BasePointTempFactory
{
    private BasePointTempFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBasePointTemp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBasePointTemp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D7E1E6EC") ,com.kingdee.eas.fdc.costindexdb.database.IBasePointTemp.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.database.IBasePointTemp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBasePointTemp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D7E1E6EC") ,com.kingdee.eas.fdc.costindexdb.database.IBasePointTemp.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBasePointTemp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBasePointTemp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D7E1E6EC"));
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IBasePointTemp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IBasePointTemp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D7E1E6EC"));
    }
}