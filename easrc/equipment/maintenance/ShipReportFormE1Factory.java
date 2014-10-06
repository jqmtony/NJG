package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ShipReportFormE1Factory
{
    private ShipReportFormE1Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IShipReportFormE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IShipReportFormE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BA308D50") ,com.kingdee.eas.port.equipment.maintenance.IShipReportFormE1.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IShipReportFormE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IShipReportFormE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BA308D50") ,com.kingdee.eas.port.equipment.maintenance.IShipReportFormE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IShipReportFormE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IShipReportFormE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BA308D50"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IShipReportFormE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IShipReportFormE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BA308D50"));
    }
}