package com.kingdee.eas.port.pm.fi;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayRequestBillEntryFactory
{
    private PayRequestBillEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E3E367F5") ,com.kingdee.eas.port.pm.fi.IPayRequestBillEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E3E367F5") ,com.kingdee.eas.port.pm.fi.IPayRequestBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E3E367F5"));
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E3E367F5"));
    }
}