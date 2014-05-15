package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CzUnitFactory
{
    private CzUnitFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.ICzUnit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ICzUnit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B2E86F5D") ,com.kingdee.eas.port.equipment.base.ICzUnit.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.ICzUnit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ICzUnit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B2E86F5D") ,com.kingdee.eas.port.equipment.base.ICzUnit.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.ICzUnit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ICzUnit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B2E86F5D"));
    }
    public static com.kingdee.eas.port.equipment.base.ICzUnit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ICzUnit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B2E86F5D"));
    }
}