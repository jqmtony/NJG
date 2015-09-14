package com.kingdee.eas.fdc.dahuaschedule.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DahuaScheduleFactory
{
    private DahuaScheduleFactory()
    {
    }
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaSchedule getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaSchedule)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BD91F978") ,com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaSchedule.class);
    }
    
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaSchedule getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaSchedule)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BD91F978") ,com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaSchedule.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaSchedule getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaSchedule)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BD91F978"));
    }
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaSchedule getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaSchedule)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BD91F978"));
    }
}