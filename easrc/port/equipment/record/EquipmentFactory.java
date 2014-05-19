package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquipmentFactory
{
    private EquipmentFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.record.IEquipment getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquipment)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3C6AD10C") ,com.kingdee.eas.port.equipment.record.IEquipment.class);
    }
    
    public static com.kingdee.eas.port.equipment.record.IEquipment getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquipment)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3C6AD10C") ,com.kingdee.eas.port.equipment.record.IEquipment.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.record.IEquipment getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquipment)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3C6AD10C"));
    }
    public static com.kingdee.eas.port.equipment.record.IEquipment getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquipment)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3C6AD10C"));
    }
}