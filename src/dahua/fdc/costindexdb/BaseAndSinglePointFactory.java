package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BaseAndSinglePointFactory
{
    private BaseAndSinglePointFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePoint getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePoint)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4B9566A8") ,com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePoint.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePoint getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePoint)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4B9566A8") ,com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePoint.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePoint getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePoint)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4B9566A8"));
    }
    public static com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePoint getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePoint)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4B9566A8"));
    }
}