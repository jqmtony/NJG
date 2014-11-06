package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MeasurEquipFactory
{
    private MeasurEquipFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.record.IMeasurEquip getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IMeasurEquip)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8967FC87") ,com.kingdee.eas.port.equipment.record.IMeasurEquip.class);
    }
    
    public static com.kingdee.eas.port.equipment.record.IMeasurEquip getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IMeasurEquip)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8967FC87") ,com.kingdee.eas.port.equipment.record.IMeasurEquip.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.record.IMeasurEquip getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IMeasurEquip)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8967FC87"));
    }
    public static com.kingdee.eas.port.equipment.record.IMeasurEquip getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IMeasurEquip)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8967FC87"));
    }
}