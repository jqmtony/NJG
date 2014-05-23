package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OverhaulNoticeFactory
{
    private OverhaulNoticeFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.IOverhaulNotice getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IOverhaulNotice)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D2BED306") ,com.kingdee.eas.port.equipment.special.IOverhaulNotice.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.IOverhaulNotice getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IOverhaulNotice)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D2BED306") ,com.kingdee.eas.port.equipment.special.IOverhaulNotice.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.IOverhaulNotice getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IOverhaulNotice)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D2BED306"));
    }
    public static com.kingdee.eas.port.equipment.special.IOverhaulNotice getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IOverhaulNotice)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D2BED306"));
    }
}