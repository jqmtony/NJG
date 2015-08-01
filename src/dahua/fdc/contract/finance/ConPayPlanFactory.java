package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConPayPlanFactory
{
    private ConPayPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D2099A5E") ,com.kingdee.eas.fdc.finance.IConPayPlan.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IConPayPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D2099A5E") ,com.kingdee.eas.fdc.finance.IConPayPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D2099A5E"));
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D2099A5E"));
    }
}