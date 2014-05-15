package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class QuarterTimeFactory
{
    private QuarterTimeFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IQuarterTime getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IQuarterTime)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0B3E3417") ,com.kingdee.eas.port.equipment.base.IQuarterTime.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IQuarterTime getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IQuarterTime)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0B3E3417") ,com.kingdee.eas.port.equipment.base.IQuarterTime.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IQuarterTime getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IQuarterTime)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0B3E3417"));
    }
    public static com.kingdee.eas.port.equipment.base.IQuarterTime getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IQuarterTime)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0B3E3417"));
    }
}