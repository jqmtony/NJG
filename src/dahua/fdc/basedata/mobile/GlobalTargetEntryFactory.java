package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GlobalTargetEntryFactory
{
    private GlobalTargetEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F294FC6A") ,com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F294FC6A") ,com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F294FC6A"));
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F294FC6A"));
    }
}