package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostAccountAssignFactory
{
    private CostAccountAssignFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountAssign getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountAssign)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("234BA1FD") ,com.kingdee.eas.fdc.basedata.ICostAccountAssign.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ICostAccountAssign getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountAssign)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("234BA1FD") ,com.kingdee.eas.fdc.basedata.ICostAccountAssign.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountAssign getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountAssign)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("234BA1FD"));
    }
    public static com.kingdee.eas.fdc.basedata.ICostAccountAssign getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostAccountAssign)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("234BA1FD"));
    }
}