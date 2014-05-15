package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaintenanceTypeFactory
{
    private MaintenanceTypeFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IMaintenanceType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IMaintenanceType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4FCCC66B") ,com.kingdee.eas.port.equipment.base.IMaintenanceType.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IMaintenanceType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IMaintenanceType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4FCCC66B") ,com.kingdee.eas.port.equipment.base.IMaintenanceType.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IMaintenanceType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IMaintenanceType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4FCCC66B"));
    }
    public static com.kingdee.eas.port.equipment.base.IMaintenanceType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IMaintenanceType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4FCCC66B"));
    }
}