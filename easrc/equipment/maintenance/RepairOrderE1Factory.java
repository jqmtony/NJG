package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RepairOrderE1Factory
{
    private RepairOrderE1Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IRepairOrderE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IRepairOrderE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("58358BBD") ,com.kingdee.eas.port.equipment.maintenance.IRepairOrderE1.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IRepairOrderE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IRepairOrderE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("58358BBD") ,com.kingdee.eas.port.equipment.maintenance.IRepairOrderE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IRepairOrderE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IRepairOrderE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("58358BBD"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IRepairOrderE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IRepairOrderE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("58358BBD"));
    }
}