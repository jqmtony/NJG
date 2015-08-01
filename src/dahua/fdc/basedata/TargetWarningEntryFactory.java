package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TargetWarningEntryFactory
{
    private TargetWarningEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ITargetWarningEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetWarningEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("73D5BCD9") ,com.kingdee.eas.fdc.basedata.ITargetWarningEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ITargetWarningEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetWarningEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("73D5BCD9") ,com.kingdee.eas.fdc.basedata.ITargetWarningEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ITargetWarningEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetWarningEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("73D5BCD9"));
    }
    public static com.kingdee.eas.fdc.basedata.ITargetWarningEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetWarningEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("73D5BCD9"));
    }
}