package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ChangeReasonFactory
{
    private ChangeReasonFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IChangeReason getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IChangeReason)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FEF74A06") ,com.kingdee.eas.fdc.basedata.IChangeReason.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IChangeReason getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IChangeReason)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FEF74A06") ,com.kingdee.eas.fdc.basedata.IChangeReason.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IChangeReason getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IChangeReason)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FEF74A06"));
    }
    public static com.kingdee.eas.fdc.basedata.IChangeReason getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IChangeReason)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FEF74A06"));
    }
}