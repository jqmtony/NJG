package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentLayoutFactory
{
    private PaymentLayoutFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPaymentLayout getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayout)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("08C83275") ,com.kingdee.eas.fdc.contract.IPaymentLayout.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPaymentLayout getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayout)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("08C83275") ,com.kingdee.eas.fdc.contract.IPaymentLayout.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPaymentLayout getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayout)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("08C83275"));
    }
    public static com.kingdee.eas.fdc.contract.IPaymentLayout getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayout)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("08C83275"));
    }
}