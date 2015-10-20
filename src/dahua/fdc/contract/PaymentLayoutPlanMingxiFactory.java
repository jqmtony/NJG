package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentLayoutPlanMingxiFactory
{
    private PaymentLayoutPlanMingxiFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPaymentLayoutPlanMingxi getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayoutPlanMingxi)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EAA966E4") ,com.kingdee.eas.fdc.contract.IPaymentLayoutPlanMingxi.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPaymentLayoutPlanMingxi getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayoutPlanMingxi)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EAA966E4") ,com.kingdee.eas.fdc.contract.IPaymentLayoutPlanMingxi.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPaymentLayoutPlanMingxi getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayoutPlanMingxi)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EAA966E4"));
    }
    public static com.kingdee.eas.fdc.contract.IPaymentLayoutPlanMingxi getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayoutPlanMingxi)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EAA966E4"));
    }
}