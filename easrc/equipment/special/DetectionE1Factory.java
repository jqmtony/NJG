package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DetectionE1Factory
{
    private DetectionE1Factory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.IDetectionE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IDetectionE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3B0E3727") ,com.kingdee.eas.port.equipment.special.IDetectionE1.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.IDetectionE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IDetectionE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3B0E3727") ,com.kingdee.eas.port.equipment.special.IDetectionE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.IDetectionE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IDetectionE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3B0E3727"));
    }
    public static com.kingdee.eas.port.equipment.special.IDetectionE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IDetectionE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3B0E3727"));
    }
}