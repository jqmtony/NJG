package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayPlanNewFactory
{
    private PayPlanNewFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNew getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNew)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A633823E") ,com.kingdee.eas.fdc.finance.IPayPlanNew.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IPayPlanNew getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNew)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A633823E") ,com.kingdee.eas.fdc.finance.IPayPlanNew.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNew getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNew)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A633823E"));
    }
    public static com.kingdee.eas.fdc.finance.IPayPlanNew getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IPayPlanNew)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A633823E"));
    }
}