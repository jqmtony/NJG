package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BaseAndSinglePointEcostFactory
{
    private BaseAndSinglePointEcostFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEcost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEcost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DA8387EA") ,com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEcost.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEcost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEcost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DA8387EA") ,com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEcost.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEcost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEcost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DA8387EA"));
    }
    public static com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEcost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEcost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DA8387EA"));
    }
}