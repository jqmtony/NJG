package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FaLocationFactory
{
    private FaLocationFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IFaLocation getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IFaLocation)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("054E1D52") ,com.kingdee.eas.port.equipment.base.IFaLocation.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IFaLocation getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IFaLocation)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("054E1D52") ,com.kingdee.eas.port.equipment.base.IFaLocation.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IFaLocation getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IFaLocation)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("054E1D52"));
    }
    public static com.kingdee.eas.port.equipment.base.IFaLocation getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IFaLocation)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("054E1D52"));
    }
}