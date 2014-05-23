package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EqmScrapFactory
{
    private EqmScrapFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.operate.IEqmScrap getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEqmScrap)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("08D6068F") ,com.kingdee.eas.port.equipment.operate.IEqmScrap.class);
    }
    
    public static com.kingdee.eas.port.equipment.operate.IEqmScrap getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEqmScrap)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("08D6068F") ,com.kingdee.eas.port.equipment.operate.IEqmScrap.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.operate.IEqmScrap getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEqmScrap)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("08D6068F"));
    }
    public static com.kingdee.eas.port.equipment.operate.IEqmScrap getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEqmScrap)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("08D6068F"));
    }
}