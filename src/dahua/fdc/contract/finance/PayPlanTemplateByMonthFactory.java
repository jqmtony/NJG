package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayPlanTemplateByMonthFactory
{
    private PayPlanTemplateByMonthFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanTemplateByMonth getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanTemplateByMonth)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("67E9CE2D") ,com.kingdee.eas.fdc.finance.IPayPlanTemplateByMonth.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayPlanTemplateByMonth getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanTemplateByMonth)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("67E9CE2D") ,com.kingdee.eas.fdc.finance.IPayPlanTemplateByMonth.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanTemplateByMonth getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanTemplateByMonth)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("67E9CE2D"));
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanTemplateByMonth getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanTemplateByMonth)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("67E9CE2D"));
    }
}