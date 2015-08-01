package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class VoucherForProjectFactory
{
    private VoucherForProjectFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IVoucherForProject getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IVoucherForProject)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DBCAE6AC") ,com.kingdee.eas.fdc.basedata.IVoucherForProject.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IVoucherForProject getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IVoucherForProject)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DBCAE6AC") ,com.kingdee.eas.fdc.basedata.IVoucherForProject.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IVoucherForProject getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IVoucherForProject)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DBCAE6AC"));
    }
    public static com.kingdee.eas.fdc.basedata.IVoucherForProject getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IVoucherForProject)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DBCAE6AC"));
    }
}