package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FdcSqlExeLogFactory
{
    private FdcSqlExeLogFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IFdcSqlExeLog getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFdcSqlExeLog)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("73C9F94D") ,com.kingdee.eas.fdc.basedata.IFdcSqlExeLog.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IFdcSqlExeLog getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFdcSqlExeLog)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("73C9F94D") ,com.kingdee.eas.fdc.basedata.IFdcSqlExeLog.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IFdcSqlExeLog getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFdcSqlExeLog)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("73C9F94D"));
    }
    public static com.kingdee.eas.fdc.basedata.IFdcSqlExeLog getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IFdcSqlExeLog)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("73C9F94D"));
    }
}