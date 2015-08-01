package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayPlanNewByScheduleTaskFactory
{
    private PayPlanNewByScheduleTaskFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTask getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTask)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C6383891") ,com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTask.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTask getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTask)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C6383891") ,com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTask.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTask getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTask)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C6383891"));
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTask getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTask)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C6383891"));
    }
}