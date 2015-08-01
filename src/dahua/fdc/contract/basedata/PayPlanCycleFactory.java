package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayPlanCycleFactory
{
    private PayPlanCycleFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IPayPlanCycle getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IPayPlanCycle)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("39599767") ,com.kingdee.eas.fdc.basedata.IPayPlanCycle.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IPayPlanCycle getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IPayPlanCycle)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("39599767") ,com.kingdee.eas.fdc.basedata.IPayPlanCycle.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IPayPlanCycle getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IPayPlanCycle)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("39599767"));
    }
    public static com.kingdee.eas.fdc.basedata.IPayPlanCycle getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IPayPlanCycle)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("39599767"));
    }
}