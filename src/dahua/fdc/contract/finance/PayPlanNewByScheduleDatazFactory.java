package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayPlanNewByScheduleDatazFactory
{
    private PayPlanNewByScheduleDatazFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleDataz getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleDataz)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FFED6484") ,com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleDataz.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleDataz getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleDataz)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FFED6484") ,com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleDataz.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleDataz getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleDataz)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FFED6484"));
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleDataz getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNewByScheduleDataz)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FFED6484"));
    }
}