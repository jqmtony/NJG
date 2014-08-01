package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InspectionEquFactory
{
    private InspectionEquFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IInspectionEqu getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IInspectionEqu)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("791AE6E5") ,com.kingdee.eas.port.equipment.maintenance.IInspectionEqu.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IInspectionEqu getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IInspectionEqu)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("791AE6E5") ,com.kingdee.eas.port.equipment.maintenance.IInspectionEqu.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IInspectionEqu getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IInspectionEqu)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("791AE6E5"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IInspectionEqu getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IInspectionEqu)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("791AE6E5"));
    }
}