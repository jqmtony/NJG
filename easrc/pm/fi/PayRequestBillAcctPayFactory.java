package com.kingdee.eas.port.pm.fi;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayRequestBillAcctPayFactory
{
    private PayRequestBillAcctPayFactory()
    {
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillAcctPay getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillAcctPay)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("915BCE58") ,com.kingdee.eas.port.pm.fi.IPayRequestBillAcctPay.class);
    }
    
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillAcctPay getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillAcctPay)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("915BCE58") ,com.kingdee.eas.port.pm.fi.IPayRequestBillAcctPay.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillAcctPay getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillAcctPay)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("915BCE58"));
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillAcctPay getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillAcctPay)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("915BCE58"));
    }
}