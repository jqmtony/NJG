package com.kingdee.eas.fdc.costdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DBAimCostFactory
{
    private DBAimCostFactory()
    {
    }
    public static com.kingdee.eas.fdc.costdb.IDBAimCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBAimCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("675AE052") ,com.kingdee.eas.fdc.costdb.IDBAimCost.class);
    }
    
    public static com.kingdee.eas.fdc.costdb.IDBAimCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBAimCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("675AE052") ,com.kingdee.eas.fdc.costdb.IDBAimCost.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costdb.IDBAimCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBAimCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("675AE052"));
    }
    public static com.kingdee.eas.fdc.costdb.IDBAimCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costdb.IDBAimCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("675AE052"));
    }
}