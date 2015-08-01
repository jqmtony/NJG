package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConPayPlanByScheduleDatazFactory
{
    private ConPayPlanByScheduleDatazFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanByScheduleDataz getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanByScheduleDataz)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CC600864") ,com.kingdee.eas.fdc.finance.IConPayPlanByScheduleDataz.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IConPayPlanByScheduleDataz getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanByScheduleDataz)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CC600864") ,com.kingdee.eas.fdc.finance.IConPayPlanByScheduleDataz.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanByScheduleDataz getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanByScheduleDataz)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CC600864"));
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanByScheduleDataz getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanByScheduleDataz)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CC600864"));
    }
}