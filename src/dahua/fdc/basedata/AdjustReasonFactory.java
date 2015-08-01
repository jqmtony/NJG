package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AdjustReasonFactory
{
    private AdjustReasonFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IAdjustReason getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAdjustReason)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3880CAA5") ,com.kingdee.eas.fdc.basedata.IAdjustReason.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IAdjustReason getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAdjustReason)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3880CAA5") ,com.kingdee.eas.fdc.basedata.IAdjustReason.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IAdjustReason getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAdjustReason)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3880CAA5"));
    }
    public static com.kingdee.eas.fdc.basedata.IAdjustReason getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAdjustReason)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3880CAA5"));
    }
}