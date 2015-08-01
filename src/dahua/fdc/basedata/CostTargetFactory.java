package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CostTargetFactory
{
    private CostTargetFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ICostTarget getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostTarget)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2499D030") ,com.kingdee.eas.fdc.basedata.ICostTarget.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ICostTarget getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostTarget)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2499D030") ,com.kingdee.eas.fdc.basedata.ICostTarget.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ICostTarget getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostTarget)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2499D030"));
    }
    public static com.kingdee.eas.fdc.basedata.ICostTarget getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ICostTarget)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2499D030"));
    }
}