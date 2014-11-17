package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquMaintBookE1Factory
{
    private EquMaintBookE1Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D4DC1037") ,com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE1.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D4DC1037") ,com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D4DC1037"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D4DC1037"));
    }
}