package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquTypeFactory
{
    private EquTypeFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IEquType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IEquType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("064DAFA1") ,com.kingdee.eas.port.equipment.base.IEquType.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IEquType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IEquType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("064DAFA1") ,com.kingdee.eas.port.equipment.base.IEquType.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IEquType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IEquType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("064DAFA1"));
    }
    public static com.kingdee.eas.port.equipment.base.IEquType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IEquType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("064DAFA1"));
    }
}