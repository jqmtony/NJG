package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonthTimeFactory
{
    private MonthTimeFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IMonthTime getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IMonthTime)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3F54B16B") ,com.kingdee.eas.port.equipment.base.IMonthTime.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IMonthTime getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IMonthTime)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3F54B16B") ,com.kingdee.eas.port.equipment.base.IMonthTime.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IMonthTime getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IMonthTime)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3F54B16B"));
    }
    public static com.kingdee.eas.port.equipment.base.IMonthTime getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IMonthTime)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3F54B16B"));
    }
}