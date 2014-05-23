package com.kingdee.eas.port.equipment.wdx;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EqmOverhaulFactory
{
    private EqmOverhaulFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.wdx.IEqmOverhaul getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.wdx.IEqmOverhaul)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("803AE46D") ,com.kingdee.eas.port.equipment.wdx.IEqmOverhaul.class);
    }
    
    public static com.kingdee.eas.port.equipment.wdx.IEqmOverhaul getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.wdx.IEqmOverhaul)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("803AE46D") ,com.kingdee.eas.port.equipment.wdx.IEqmOverhaul.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.wdx.IEqmOverhaul getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.wdx.IEqmOverhaul)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("803AE46D"));
    }
    public static com.kingdee.eas.port.equipment.wdx.IEqmOverhaul getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.wdx.IEqmOverhaul)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("803AE46D"));
    }
}