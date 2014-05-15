package com.kingdee.eas.port.equipment.record;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EquIdSpareInfoFactory
{
    private EquIdSpareInfoFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.record.IEquIdSpareInfo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquIdSpareInfo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("59736383") ,com.kingdee.eas.port.equipment.record.IEquIdSpareInfo.class);
    }
    
    public static com.kingdee.eas.port.equipment.record.IEquIdSpareInfo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquIdSpareInfo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("59736383") ,com.kingdee.eas.port.equipment.record.IEquIdSpareInfo.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.record.IEquIdSpareInfo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquIdSpareInfo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("59736383"));
    }
    public static com.kingdee.eas.port.equipment.record.IEquIdSpareInfo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.record.IEquIdSpareInfo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("59736383"));
    }
}