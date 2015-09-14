package com.kingdee.eas.fdc.dahuaschedule.schedule;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DahuaScheduleEntryFactory
{
    private DahuaScheduleEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CC8749DA") ,com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleEntry.class);
    }
    
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CC8749DA") ,com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CC8749DA"));
    }
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.IDahuaScheduleEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CC8749DA"));
    }
}