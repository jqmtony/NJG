package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayPlanNewUnsignFactory
{
    private PayPlanNewUnsignFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewUnsign getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewUnsign)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("451C3FB4") ,com.kingdee.eas.fdc.finance.IPayPlanNewUnsign.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayPlanNewUnsign getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewUnsign)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("451C3FB4") ,com.kingdee.eas.fdc.finance.IPayPlanNewUnsign.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewUnsign getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewUnsign)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("451C3FB4"));
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewUnsign getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewUnsign)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("451C3FB4"));
    }
}