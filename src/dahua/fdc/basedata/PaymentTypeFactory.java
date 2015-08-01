package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PaymentTypeFactory
{
    private PaymentTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IPaymentType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IPaymentType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DE38402E") ,com.kingdee.eas.fdc.basedata.IPaymentType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IPaymentType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IPaymentType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DE38402E") ,com.kingdee.eas.fdc.basedata.IPaymentType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IPaymentType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IPaymentType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DE38402E"));
    }
    public static com.kingdee.eas.fdc.basedata.IPaymentType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IPaymentType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DE38402E"));
    }
}