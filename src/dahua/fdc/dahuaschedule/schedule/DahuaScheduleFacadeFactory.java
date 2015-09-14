package com.kingdee.eas.fdc.dahuaschedule.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DahuaScheduleFacadeFactory
{
    private DahuaScheduleFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C5579C32") ,com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade.class);
    }
    
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C5579C32") ,com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C5579C32"));
    }
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C5579C32"));
    }
}