package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquLeaseE1Factory
{
    private EquLeaseE1Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.operate.IEquLeaseE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEquLeaseE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FAAE969C") ,com.kingdee.eas.port.equipment.operate.IEquLeaseE1.class);
    }
    
    public static com.kingdee.eas.port.equipment.operate.IEquLeaseE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEquLeaseE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FAAE969C") ,com.kingdee.eas.port.equipment.operate.IEquLeaseE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.operate.IEquLeaseE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEquLeaseE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FAAE969C"));
    }
    public static com.kingdee.eas.port.equipment.operate.IEquLeaseE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEquLeaseE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FAAE969C"));
    }
}