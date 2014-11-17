package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquMaintBookE2Factory
{
    private EquMaintBookE2Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D4DC1038") ,com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE2.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D4DC1038") ,com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE2.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D4DC1038"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D4DC1038"));
    }
}