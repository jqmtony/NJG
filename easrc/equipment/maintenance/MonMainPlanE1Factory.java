package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonMainPlanE1Factory
{
    private MonMainPlanE1Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IMonMainPlanE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMainPlanE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A24DDC6A") ,com.kingdee.eas.port.equipment.maintenance.IMonMainPlanE1.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IMonMainPlanE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMainPlanE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A24DDC6A") ,com.kingdee.eas.port.equipment.maintenance.IMonMainPlanE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IMonMainPlanE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMainPlanE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A24DDC6A"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IMonMainPlanE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMainPlanE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A24DDC6A"));
    }
}