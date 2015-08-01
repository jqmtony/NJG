package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayPlanTemplateFactory
{
    private PayPlanTemplateFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E60DA69C") ,com.kingdee.eas.fdc.finance.IPayPlanTemplate.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayPlanTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E60DA69C") ,com.kingdee.eas.fdc.finance.IPayPlanTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E60DA69C"));
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E60DA69C"));
    }
}