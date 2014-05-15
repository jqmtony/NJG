package com.kingdee.eas.port.equipment.wdx;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EqmOverhaulE1Factory
{
    private EqmOverhaulE1Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.wdx.IEqmOverhaulE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.wdx.IEqmOverhaulE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5D1385B9") ,com.kingdee.eas.port.equipment.wdx.IEqmOverhaulE1.class);
    }
    
    public static com.kingdee.eas.port.equipment.wdx.IEqmOverhaulE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.wdx.IEqmOverhaulE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5D1385B9") ,com.kingdee.eas.port.equipment.wdx.IEqmOverhaulE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.wdx.IEqmOverhaulE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.wdx.IEqmOverhaulE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5D1385B9"));
    }
    public static com.kingdee.eas.port.equipment.wdx.IEqmOverhaulE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.wdx.IEqmOverhaulE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5D1385B9"));
    }
}