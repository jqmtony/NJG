package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DetectionE2Factory
{
    private DetectionE2Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.IDetectionE2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IDetectionE2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3B0E3728") ,com.kingdee.eas.port.equipment.special.IDetectionE2.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.IDetectionE2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IDetectionE2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3B0E3728") ,com.kingdee.eas.port.equipment.special.IDetectionE2.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.IDetectionE2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IDetectionE2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3B0E3728"));
    }
    public static com.kingdee.eas.port.equipment.special.IDetectionE2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IDetectionE2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3B0E3728"));
    }
}