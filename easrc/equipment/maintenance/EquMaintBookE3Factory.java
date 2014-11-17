package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquMaintBookE3Factory
{
    private EquMaintBookE3Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE3 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE3)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D4DC1039") ,com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE3.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE3 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE3)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D4DC1039") ,com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE3.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE3 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE3)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D4DC1039"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE3 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBookE3)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D4DC1039"));
    }
}