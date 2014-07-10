package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquLeaseFactory
{
    private EquLeaseFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.operate.IEquLease getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEquLease)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("161AD210") ,com.kingdee.eas.port.equipment.operate.IEquLease.class);
    }
    
    public static com.kingdee.eas.port.equipment.operate.IEquLease getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEquLease)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("161AD210") ,com.kingdee.eas.port.equipment.operate.IEquLease.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.operate.IEquLease getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEquLease)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("161AD210"));
    }
    public static com.kingdee.eas.port.equipment.operate.IEquLease getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEquLease)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("161AD210"));
    }
}