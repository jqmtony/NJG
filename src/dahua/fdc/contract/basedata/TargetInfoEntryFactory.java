package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TargetInfoEntryFactory
{
    private TargetInfoEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ITargetInfoEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetInfoEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AD0D38A1") ,com.kingdee.eas.fdc.basedata.ITargetInfoEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ITargetInfoEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetInfoEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AD0D38A1") ,com.kingdee.eas.fdc.basedata.ITargetInfoEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ITargetInfoEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetInfoEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AD0D38A1"));
    }
    public static com.kingdee.eas.fdc.basedata.ITargetInfoEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetInfoEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AD0D38A1"));
    }
}