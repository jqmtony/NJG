package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConPayPlanByScheduleTaskFactory
{
    private ConPayPlanByScheduleTaskFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanByScheduleTask getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanByScheduleTask)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("50F198B1") ,com.kingdee.eas.fdc.finance.IConPayPlanByScheduleTask.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IConPayPlanByScheduleTask getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanByScheduleTask)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("50F198B1") ,com.kingdee.eas.fdc.finance.IConPayPlanByScheduleTask.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanByScheduleTask getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanByScheduleTask)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("50F198B1"));
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanByScheduleTask getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanByScheduleTask)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("50F198B1"));
    }
}