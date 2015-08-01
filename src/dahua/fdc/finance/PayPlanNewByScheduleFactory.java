package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayPlanNewByScheduleFactory
{
    private PayPlanNewByScheduleFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewBySchedule getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewBySchedule)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DE24E4EC") ,com.kingdee.eas.fdc.finance.IPayPlanNewBySchedule.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayPlanNewBySchedule getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewBySchedule)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DE24E4EC") ,com.kingdee.eas.fdc.finance.IPayPlanNewBySchedule.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewBySchedule getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewBySchedule)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DE24E4EC"));
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewBySchedule getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewBySchedule)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DE24E4EC"));
    }
}