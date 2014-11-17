package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TankTechnicalFactory
{
    private TankTechnicalFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.record.ITankTechnical getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.ITankTechnical)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E3760E11") ,com.kingdee.eas.port.equipment.record.ITankTechnical.class);
    }
    
    public static com.kingdee.eas.port.equipment.record.ITankTechnical getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.ITankTechnical)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E3760E11") ,com.kingdee.eas.port.equipment.record.ITankTechnical.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.record.ITankTechnical getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.ITankTechnical)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E3760E11"));
    }
    public static com.kingdee.eas.port.equipment.record.ITankTechnical getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.ITankTechnical)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E3760E11"));
    }
}