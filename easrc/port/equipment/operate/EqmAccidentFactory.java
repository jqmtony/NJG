package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EqmAccidentFactory
{
    private EqmAccidentFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.operate.IEqmAccident getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEqmAccident)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DB643291") ,com.kingdee.eas.port.equipment.operate.IEqmAccident.class);
    }
    
    public static com.kingdee.eas.port.equipment.operate.IEqmAccident getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEqmAccident)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DB643291") ,com.kingdee.eas.port.equipment.operate.IEqmAccident.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.operate.IEqmAccident getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEqmAccident)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DB643291"));
    }
    public static com.kingdee.eas.port.equipment.operate.IEqmAccident getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IEqmAccident)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DB643291"));
    }
}