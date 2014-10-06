package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ShipFuelFactory
{
    private ShipFuelFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.operate.IShipFuel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IShipFuel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D5C68991") ,com.kingdee.eas.port.equipment.operate.IShipFuel.class);
    }
    
    public static com.kingdee.eas.port.equipment.operate.IShipFuel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IShipFuel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D5C68991") ,com.kingdee.eas.port.equipment.operate.IShipFuel.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.operate.IShipFuel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IShipFuel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D5C68991"));
    }
    public static com.kingdee.eas.port.equipment.operate.IShipFuel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IShipFuel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D5C68991"));
    }
}