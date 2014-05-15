package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MonMPApplicationE1Factory
{
    private MonMPApplicationE1Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IMonMPApplicationE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMPApplicationE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B99906BD") ,com.kingdee.eas.port.equipment.maintenance.IMonMPApplicationE1.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IMonMPApplicationE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMPApplicationE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B99906BD") ,com.kingdee.eas.port.equipment.maintenance.IMonMPApplicationE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IMonMPApplicationE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMPApplicationE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B99906BD"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IMonMPApplicationE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IMonMPApplicationE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B99906BD"));
    }
}