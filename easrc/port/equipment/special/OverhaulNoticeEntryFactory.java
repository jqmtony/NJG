package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OverhaulNoticeEntryFactory
{
    private OverhaulNoticeEntryFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.IOverhaulNoticeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IOverhaulNoticeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("50CA390C") ,com.kingdee.eas.port.equipment.special.IOverhaulNoticeEntry.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.IOverhaulNoticeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IOverhaulNoticeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("50CA390C") ,com.kingdee.eas.port.equipment.special.IOverhaulNoticeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.IOverhaulNoticeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IOverhaulNoticeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("50CA390C"));
    }
    public static com.kingdee.eas.port.equipment.special.IOverhaulNoticeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IOverhaulNoticeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("50CA390C"));
    }
}