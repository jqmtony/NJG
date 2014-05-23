package com.kingdee.eas.port.pm.fi;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayRequestBillBgEntryFactory
{
    private PayRequestBillBgEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillBgEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillBgEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CB6C79F0") ,com.kingdee.eas.port.pm.fi.IPayRequestBillBgEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillBgEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillBgEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CB6C79F0") ,com.kingdee.eas.port.pm.fi.IPayRequestBillBgEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillBgEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillBgEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CB6C79F0"));
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillBgEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillBgEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CB6C79F0"));
    }
}