package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonMainPlanFactory
{
    private MonMainPlanFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IMonMainPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMainPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A0CD335E") ,com.kingdee.eas.port.equipment.maintenance.IMonMainPlan.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IMonMainPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMainPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A0CD335E") ,com.kingdee.eas.port.equipment.maintenance.IMonMainPlan.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IMonMainPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMainPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A0CD335E"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IMonMainPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMainPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A0CD335E"));
    }
}