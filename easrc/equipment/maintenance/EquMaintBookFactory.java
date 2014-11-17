package com.kingdee.eas.port.equipment.maintenance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquMaintBookFactory
{
    private EquMaintBookFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBook getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBook)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("14FFF66B") ,com.kingdee.eas.port.equipment.maintenance.IEquMaintBook.class);
    }
    
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBook getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBook)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("14FFF66B") ,com.kingdee.eas.port.equipment.maintenance.IEquMaintBook.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBook getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBook)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("14FFF66B"));
    }
    public static com.kingdee.eas.port.equipment.maintenance.IEquMaintBook getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.maintenance.IEquMaintBook)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("14FFF66B"));
    }
}