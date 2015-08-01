package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class InvalidCostReasonFactory
{
    private InvalidCostReasonFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IInvalidCostReason getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IInvalidCostReason)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F042D116") ,com.kingdee.eas.fdc.basedata.IInvalidCostReason.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IInvalidCostReason getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IInvalidCostReason)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F042D116") ,com.kingdee.eas.fdc.basedata.IInvalidCostReason.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IInvalidCostReason getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IInvalidCostReason)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F042D116"));
    }
    public static com.kingdee.eas.fdc.basedata.IInvalidCostReason getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IInvalidCostReason)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F042D116"));
    }
}