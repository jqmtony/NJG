package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InitialConnectionFactory
{
    private InitialConnectionFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IInitialConnection getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInitialConnection)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E6B39740") ,com.kingdee.eas.port.equipment.base.IInitialConnection.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IInitialConnection getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInitialConnection)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E6B39740") ,com.kingdee.eas.port.equipment.base.IInitialConnection.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IInitialConnection getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInitialConnection)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E6B39740"));
    }
    public static com.kingdee.eas.port.equipment.base.IInitialConnection getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IInitialConnection)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E6B39740"));
    }
}