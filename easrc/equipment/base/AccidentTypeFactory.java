package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AccidentTypeFactory
{
    private AccidentTypeFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IAccidentType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IAccidentType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9857C52B") ,com.kingdee.eas.port.equipment.base.IAccidentType.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IAccidentType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IAccidentType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9857C52B") ,com.kingdee.eas.port.equipment.base.IAccidentType.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IAccidentType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IAccidentType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9857C52B"));
    }
    public static com.kingdee.eas.port.equipment.base.IAccidentType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IAccidentType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9857C52B"));
    }
}