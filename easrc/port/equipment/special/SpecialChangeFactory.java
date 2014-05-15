package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SpecialChangeFactory
{
    private SpecialChangeFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.ISpecialChange getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.ISpecialChange)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("73EE059F") ,com.kingdee.eas.port.equipment.special.ISpecialChange.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.ISpecialChange getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.ISpecialChange)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("73EE059F") ,com.kingdee.eas.port.equipment.special.ISpecialChange.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.ISpecialChange getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.ISpecialChange)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("73EE059F"));
    }
    public static com.kingdee.eas.port.equipment.special.ISpecialChange getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.ISpecialChange)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("73EE059F"));
    }
}