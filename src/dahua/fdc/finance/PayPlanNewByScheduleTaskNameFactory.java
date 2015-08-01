package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayPlanNewByScheduleTaskNameFactory
{
    private PayPlanNewByScheduleTaskNameFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTaskName getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTaskName)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("077C76BC") ,com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTaskName.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTaskName getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTaskName)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("077C76BC") ,com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTaskName.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTaskName getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTaskName)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("077C76BC"));
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTaskName getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleTaskName)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("077C76BC"));
    }
}