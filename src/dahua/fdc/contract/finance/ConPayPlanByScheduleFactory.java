package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConPayPlanByScheduleFactory
{
    private ConPayPlanByScheduleFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanBySchedule getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanBySchedule)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C3D3550C") ,com.kingdee.eas.fdc.finance.IConPayPlanBySchedule.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IConPayPlanBySchedule getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanBySchedule)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C3D3550C") ,com.kingdee.eas.fdc.finance.IConPayPlanBySchedule.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanBySchedule getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanBySchedule)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C3D3550C"));
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanBySchedule getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanBySchedule)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C3D3550C"));
    }
}