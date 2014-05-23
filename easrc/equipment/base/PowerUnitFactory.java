package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PowerUnitFactory
{
    private PowerUnitFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IPowerUnit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IPowerUnit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BF2ED427") ,com.kingdee.eas.port.equipment.base.IPowerUnit.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IPowerUnit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IPowerUnit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BF2ED427") ,com.kingdee.eas.port.equipment.base.IPowerUnit.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IPowerUnit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IPowerUnit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BF2ED427"));
    }
    public static com.kingdee.eas.port.equipment.base.IPowerUnit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IPowerUnit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BF2ED427"));
    }
}