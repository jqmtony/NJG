package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayPlanNewByMonthFactory
{
    private PayPlanNewByMonthFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByMonth getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByMonth)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8205F84B") ,com.kingdee.eas.fdc.finance.IPayPlanNewByMonth.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByMonth getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByMonth)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8205F84B") ,com.kingdee.eas.fdc.finance.IPayPlanNewByMonth.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByMonth getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByMonth)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8205F84B"));
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByMonth getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByMonth)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8205F84B"));
    }
}