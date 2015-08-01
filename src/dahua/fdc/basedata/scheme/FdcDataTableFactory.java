package com.kingdee.eas.fdc.basedata.scheme;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FdcDataTableFactory
{
    private FdcDataTableFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcDataTable getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcDataTable)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1234DF90") ,com.kingdee.eas.fdc.basedata.scheme.IFdcDataTable.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcDataTable getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcDataTable)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1234DF90") ,com.kingdee.eas.fdc.basedata.scheme.IFdcDataTable.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcDataTable getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcDataTable)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1234DF90"));
    }
    public static com.kingdee.eas.fdc.basedata.scheme.IFdcDataTable getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.scheme.IFdcDataTable)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1234DF90"));
    }
}