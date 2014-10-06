package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ShipReportFormFactory
{
    private ShipReportFormFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IShipReportForm getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IShipReportForm)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4352E5C4") ,com.kingdee.eas.port.equipment.maintenance.IShipReportForm.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IShipReportForm getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IShipReportForm)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4352E5C4") ,com.kingdee.eas.port.equipment.maintenance.IShipReportForm.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IShipReportForm getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IShipReportForm)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4352E5C4"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IShipReportForm getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IShipReportForm)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4352E5C4"));
    }
}