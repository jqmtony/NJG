package com.kingdee.eas.fdc.basedata.scheme;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FdcColumnFactory
{
    private FdcColumnFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcColumn getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcColumn)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("99A6ED8A") ,com.kingdee.eas.fdc.basedata.scheme.IFdcColumn.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcColumn getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcColumn)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("99A6ED8A") ,com.kingdee.eas.fdc.basedata.scheme.IFdcColumn.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcColumn getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcColumn)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("99A6ED8A"));
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcColumn getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcColumn)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("99A6ED8A"));
    }
}