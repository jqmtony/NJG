package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentLayoutPayByStageFactory
{
    private PaymentLayoutPayByStageFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPaymentLayoutPayByStage getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayoutPayByStage)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7B247F74") ,com.kingdee.eas.fdc.contract.IPaymentLayoutPayByStage.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPaymentLayoutPayByStage getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayoutPayByStage)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7B247F74") ,com.kingdee.eas.fdc.contract.IPaymentLayoutPayByStage.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPaymentLayoutPayByStage getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayoutPayByStage)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7B247F74"));
    }
    public static com.kingdee.eas.fdc.contract.IPaymentLayoutPayByStage getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPaymentLayoutPayByStage)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7B247F74"));
    }
}