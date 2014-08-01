package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InspectionEquE1Factory
{
    private InspectionEquE1Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IInspectionEquE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IInspectionEquE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9DFCCA31") ,com.kingdee.eas.port.equipment.maintenance.IInspectionEquE1.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IInspectionEquE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IInspectionEquE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9DFCCA31") ,com.kingdee.eas.port.equipment.maintenance.IInspectionEquE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IInspectionEquE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IInspectionEquE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9DFCCA31"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IInspectionEquE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IInspectionEquE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9DFCCA31"));
    }
}