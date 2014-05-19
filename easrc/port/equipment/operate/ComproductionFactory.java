package com.kingdee.eas.port.equipment.operate;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ComproductionFactory
{
    private ComproductionFactory()
    {
    }
    public static com.kingdee.eas.port.equipment.operate.IComproduction getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IComproduction)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("232E84DB") ,com.kingdee.eas.port.equipment.operate.IComproduction.class);
    }
    
    public static com.kingdee.eas.port.equipment.operate.IComproduction getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IComproduction)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("232E84DB") ,com.kingdee.eas.port.equipment.operate.IComproduction.class, objectCtx);
    }
    public static com.kingdee.eas.port.equipment.operate.IComproduction getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IComproduction)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("232E84DB"));
    }
    public static com.kingdee.eas.port.equipment.operate.IComproduction getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.equipment.operate.IComproduction)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("232E84DB"));
    }
}