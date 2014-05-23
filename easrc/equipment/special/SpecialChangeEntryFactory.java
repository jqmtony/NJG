package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SpecialChangeEntryFactory
{
    private SpecialChangeEntryFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.ISpecialChangeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.ISpecialChangeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6C5FBE13") ,com.kingdee.eas.port.equipment.special.ISpecialChangeEntry.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.ISpecialChangeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.ISpecialChangeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6C5FBE13") ,com.kingdee.eas.port.equipment.special.ISpecialChangeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.ISpecialChangeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.ISpecialChangeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6C5FBE13"));
    }
    public static com.kingdee.eas.port.equipment.special.ISpecialChangeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.ISpecialChangeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6C5FBE13"));
    }
}