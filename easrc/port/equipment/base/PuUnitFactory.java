package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PuUnitFactory
{
    private PuUnitFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IPuUnit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IPuUnit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C8D0F9EB") ,com.kingdee.eas.port.equipment.base.IPuUnit.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IPuUnit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IPuUnit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C8D0F9EB") ,com.kingdee.eas.port.equipment.base.IPuUnit.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IPuUnit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IPuUnit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C8D0F9EB"));
    }
    public static com.kingdee.eas.port.equipment.base.IPuUnit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IPuUnit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C8D0F9EB"));
    }
}