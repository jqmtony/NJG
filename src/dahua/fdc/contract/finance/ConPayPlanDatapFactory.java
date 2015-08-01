package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConPayPlanDatapFactory
{
    private ConPayPlanDatapFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanDatap getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanDatap)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7A3A4648") ,com.kingdee.eas.fdc.finance.IConPayPlanDatap.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IConPayPlanDatap getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanDatap)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7A3A4648") ,com.kingdee.eas.fdc.finance.IConPayPlanDatap.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanDatap getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanDatap)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7A3A4648"));
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanDatap getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanDatap)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7A3A4648"));
    }
}