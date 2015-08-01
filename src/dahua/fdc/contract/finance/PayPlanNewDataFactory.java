package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayPlanNewDataFactory
{
    private PayPlanNewDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("802C9708") ,com.kingdee.eas.fdc.finance.IPayPlanNewData.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayPlanNewData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("802C9708") ,com.kingdee.eas.fdc.finance.IPayPlanNewData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("802C9708"));
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("802C9708"));
    }
}