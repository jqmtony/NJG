package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RepairOrderFactory
{
    private RepairOrderFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IRepairOrder getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IRepairOrder)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F96E9B71") ,com.kingdee.eas.port.equipment.maintenance.IRepairOrder.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IRepairOrder getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IRepairOrder)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F96E9B71") ,com.kingdee.eas.port.equipment.maintenance.IRepairOrder.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IRepairOrder getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IRepairOrder)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F96E9B71"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IRepairOrder getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IRepairOrder)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F96E9B71"));
    }
}