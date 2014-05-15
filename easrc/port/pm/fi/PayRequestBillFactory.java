package com.kingdee.eas.port.pm.fi;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayRequestBillFactory
{
    private PayRequestBillFactory()
    {
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9C9C367D") ,com.kingdee.eas.port.pm.fi.IPayRequestBill.class);
    }
    
    public static com.kingdee.eas.port.pm.fi.IPayRequestBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9C9C367D") ,com.kingdee.eas.port.pm.fi.IPayRequestBill.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9C9C367D"));
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9C9C367D"));
    }
}