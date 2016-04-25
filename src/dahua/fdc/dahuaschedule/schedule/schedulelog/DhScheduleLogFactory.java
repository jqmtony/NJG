package com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DhScheduleLogFactory
{
    private DhScheduleLogFactory()
    {
    }
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog.IDhScheduleLog getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog.IDhScheduleLog)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A1CC5F92") ,com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog.IDhScheduleLog.class);
    }
    
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog.IDhScheduleLog getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog.IDhScheduleLog)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A1CC5F92") ,com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog.IDhScheduleLog.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog.IDhScheduleLog getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog.IDhScheduleLog)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A1CC5F92"));
    }
    public static com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog.IDhScheduleLog getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.dahuaschedule.schedule.schedulelog.IDhScheduleLog)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A1CC5F92"));
    }
}