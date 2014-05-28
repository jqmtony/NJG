package com.kingdee.eas.port.equipment.special;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DetectionFactory
{
    private DetectionFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.special.IDetection getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IDetection)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1A6F195B") ,com.kingdee.eas.port.equipment.special.IDetection.class);
    }
    
    public static com.kingdee.eas.port.equipment.special.IDetection getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IDetection)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1A6F195B") ,com.kingdee.eas.port.equipment.special.IDetection.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.special.IDetection getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IDetection)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1A6F195B"));
    }
    public static com.kingdee.eas.port.equipment.special.IDetection getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.special.IDetection)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1A6F195B"));
    }
}