package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentLayoutPlamFactory
{
    private PaymentLayoutPlamFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPaymentLayoutPlam getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayoutPlam)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2BF5EF3D") ,com.kingdee.eas.fdc.contract.IPaymentLayoutPlam.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPaymentLayoutPlam getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayoutPlam)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2BF5EF3D") ,com.kingdee.eas.fdc.contract.IPaymentLayoutPlam.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPaymentLayoutPlam getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayoutPlam)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2BF5EF3D"));
    }
    public static com.kingdee.eas.fdc.contract.IPaymentLayoutPlam getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayoutPlam)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2BF5EF3D"));
    }
}