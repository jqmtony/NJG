package com.kingdee.eas.fdc.costindexdb;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class BaseAndSinglePointEntryFactory
{
    private BaseAndSinglePointEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DA889AAA") ,com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEntry.class);
    }
    
    public static com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DA889AAA") ,com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DA889AAA"));
    }
    public static com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.costindexdb.IBaseAndSinglePointEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DA889AAA"));
    }
}