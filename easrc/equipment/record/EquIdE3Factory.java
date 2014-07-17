package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquIdE3Factory
{
    private EquIdE3Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.record.IEquIdE3 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquIdE3)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("ACA01ED0") ,com.kingdee.eas.port.equipment.record.IEquIdE3.class);
    }
    
    public static com.kingdee.eas.port.equipment.record.IEquIdE3 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquIdE3)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("ACA01ED0") ,com.kingdee.eas.port.equipment.record.IEquIdE3.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.record.IEquIdE3 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquIdE3)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("ACA01ED0"));
    }
    public static com.kingdee.eas.port.equipment.record.IEquIdE3 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquIdE3)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("ACA01ED0"));
    }
}