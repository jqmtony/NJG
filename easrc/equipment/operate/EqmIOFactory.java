package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EqmIOFactory
{
    private EqmIOFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.operate.IEqmIO getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEqmIO)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A4EDF708") ,com.kingdee.eas.port.equipment.operate.IEqmIO.class);
    }
    
    public static com.kingdee.eas.port.equipment.operate.IEqmIO getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEqmIO)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A4EDF708") ,com.kingdee.eas.port.equipment.operate.IEqmIO.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.operate.IEqmIO getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEqmIO)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A4EDF708"));
    }
    public static com.kingdee.eas.port.equipment.operate.IEqmIO getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEqmIO)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A4EDF708"));
    }
}