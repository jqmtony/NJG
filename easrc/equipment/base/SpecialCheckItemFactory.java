package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SpecialCheckItemFactory
{
    private SpecialCheckItemFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.ISpecialCheckItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ISpecialCheckItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("77774DC4") ,com.kingdee.eas.port.equipment.base.ISpecialCheckItem.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.ISpecialCheckItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ISpecialCheckItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("77774DC4") ,com.kingdee.eas.port.equipment.base.ISpecialCheckItem.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.ISpecialCheckItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ISpecialCheckItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("77774DC4"));
    }
    public static com.kingdee.eas.port.equipment.base.ISpecialCheckItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.ISpecialCheckItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("77774DC4"));
    }
}