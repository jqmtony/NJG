package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayPlanTemplateByScheduleFactory
{
    private PayPlanTemplateByScheduleFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanTemplateBySchedule getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanTemplateBySchedule)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6A9BABCA") ,com.kingdee.eas.fdc.finance.IPayPlanTemplateBySchedule.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayPlanTemplateBySchedule getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanTemplateBySchedule)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6A9BABCA") ,com.kingdee.eas.fdc.finance.IPayPlanTemplateBySchedule.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanTemplateBySchedule getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanTemplateBySchedule)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6A9BABCA"));
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanTemplateBySchedule getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanTemplateBySchedule)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6A9BABCA"));
    }
}