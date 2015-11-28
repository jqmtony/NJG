package com.kingdee.eas.fdc.costindexdb.database;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProfessionPointFactory
{
    private ProfessionPointFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IProfessionPoint getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IProfessionPoint)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8C4A44ED") ,com.kingdee.eas.fdc.costindexdb.database.IProfessionPoint.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.database.IProfessionPoint getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IProfessionPoint)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8C4A44ED") ,com.kingdee.eas.fdc.costindexdb.database.IProfessionPoint.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IProfessionPoint getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IProfessionPoint)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8C4A44ED"));
    }
    public static com.kingdee.eas.fdc.costindexdb.database.IProfessionPoint getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.database.IProfessionPoint)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8C4A44ED"));
    }
}