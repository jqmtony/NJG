package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class YearPlanApplyE1Factory
{
    private YearPlanApplyE1Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IYearPlanApplyE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IYearPlanApplyE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7F9EE844") ,com.kingdee.eas.port.equipment.maintenance.IYearPlanApplyE1.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IYearPlanApplyE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IYearPlanApplyE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7F9EE844") ,com.kingdee.eas.port.equipment.maintenance.IYearPlanApplyE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IYearPlanApplyE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IYearPlanApplyE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7F9EE844"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IYearPlanApplyE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IYearPlanApplyE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7F9EE844"));
    }
}