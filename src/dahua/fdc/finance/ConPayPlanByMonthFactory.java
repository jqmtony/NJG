package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConPayPlanByMonthFactory
{
    private ConPayPlanByMonthFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanByMonth getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanByMonth)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("91CA7C2B") ,com.kingdee.eas.fdc.finance.IConPayPlanByMonth.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IConPayPlanByMonth getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanByMonth)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("91CA7C2B") ,com.kingdee.eas.fdc.finance.IConPayPlanByMonth.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanByMonth getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanByMonth)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("91CA7C2B"));
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanByMonth getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanByMonth)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("91CA7C2B"));
    }
}