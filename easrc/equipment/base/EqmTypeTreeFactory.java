package com.kingdee.eas.port.equipment.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EqmTypeTreeFactory
{
    private EqmTypeTreeFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.base.IEqmTypeTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IEqmTypeTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("20BF66D7") ,com.kingdee.eas.port.equipment.base.IEqmTypeTree.class);
    }
    
    public static com.kingdee.eas.port.equipment.base.IEqmTypeTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IEqmTypeTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("20BF66D7") ,com.kingdee.eas.port.equipment.base.IEqmTypeTree.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.base.IEqmTypeTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IEqmTypeTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("20BF66D7"));
    }
    public static com.kingdee.eas.port.equipment.base.IEqmTypeTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.base.IEqmTypeTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("20BF66D7"));
    }
}