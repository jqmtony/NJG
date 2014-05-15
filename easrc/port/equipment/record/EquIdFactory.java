package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquIdFactory
{
    private EquIdFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.record.IEquId getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquId)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0ED4BEC2") ,com.kingdee.eas.port.equipment.record.IEquId.class);
    }
    
    public static com.kingdee.eas.port.equipment.record.IEquId getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquId)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0ED4BEC2") ,com.kingdee.eas.port.equipment.record.IEquId.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.record.IEquId getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquId)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0ED4BEC2"));
    }
    public static com.kingdee.eas.port.equipment.record.IEquId getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquId)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0ED4BEC2"));
    }
}