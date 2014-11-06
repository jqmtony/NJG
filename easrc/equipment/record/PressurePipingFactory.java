package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PressurePipingFactory
{
    private PressurePipingFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.record.IPressurePiping getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IPressurePiping)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("59EDF8D2") ,com.kingdee.eas.port.equipment.record.IPressurePiping.class);
    }
    
    public static com.kingdee.eas.port.equipment.record.IPressurePiping getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IPressurePiping)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("59EDF8D2") ,com.kingdee.eas.port.equipment.record.IPressurePiping.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.record.IPressurePiping getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IPressurePiping)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("59EDF8D2"));
    }
    public static com.kingdee.eas.port.equipment.record.IPressurePiping getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IPressurePiping)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("59EDF8D2"));
    }
}