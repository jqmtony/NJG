package com.kingdee.eas.fi.cas;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentBillFactory
{
    private PaymentBillFactory()
    {
    }
    public static com.kingdee.eas.fi.cas.IPaymentBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fi.cas.IPaymentBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("40284E81") ,com.kingdee.eas.fi.cas.IPaymentBill.class);
    }
    
    public static com.kingdee.eas.fi.cas.IPaymentBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fi.cas.IPaymentBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("40284E81") ,com.kingdee.eas.fi.cas.IPaymentBill.class, objectCtx);
    }
    public static com.kingdee.eas.fi.cas.IPaymentBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fi.cas.IPaymentBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("40284E81"));
    }
    public static com.kingdee.eas.fi.cas.IPaymentBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fi.cas.IPaymentBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("40284E81"));
    }
}