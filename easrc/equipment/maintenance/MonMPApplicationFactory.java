package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonMPApplicationFactory
{
    private MonMPApplicationFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IMonMPApplication getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMPApplication)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C374D671") ,com.kingdee.eas.port.equipment.maintenance.IMonMPApplication.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IMonMPApplication getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMPApplication)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C374D671") ,com.kingdee.eas.port.equipment.maintenance.IMonMPApplication.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IMonMPApplication getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMPApplication)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C374D671"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IMonMPApplication getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMPApplication)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C374D671"));
    }
}