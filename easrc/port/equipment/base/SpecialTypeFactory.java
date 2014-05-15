package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SpecialTypeFactory
{
    private SpecialTypeFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.ISpecialType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ISpecialType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5D6C4011") ,com.kingdee.eas.port.equipment.base.ISpecialType.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.ISpecialType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ISpecialType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5D6C4011") ,com.kingdee.eas.port.equipment.base.ISpecialType.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.ISpecialType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ISpecialType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5D6C4011"));
    }
    public static com.kingdee.eas.port.equipment.base.ISpecialType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ISpecialType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5D6C4011"));
    }
}