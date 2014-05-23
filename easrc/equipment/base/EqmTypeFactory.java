package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EqmTypeFactory
{
    private EqmTypeFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IEqmType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IEqmType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("05DCF399") ,com.kingdee.eas.port.equipment.base.IEqmType.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IEqmType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IEqmType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("05DCF399") ,com.kingdee.eas.port.equipment.base.IEqmType.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IEqmType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IEqmType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("05DCF399"));
    }
    public static com.kingdee.eas.port.equipment.base.IEqmType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IEqmType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("05DCF399"));
    }
}