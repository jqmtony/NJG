package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TargetDescEntryFactory
{
    private TargetDescEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.ITargetDescEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetDescEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BC03549E") ,com.kingdee.eas.fdc.basedata.ITargetDescEntry.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.ITargetDescEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetDescEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BC03549E") ,com.kingdee.eas.fdc.basedata.ITargetDescEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.ITargetDescEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetDescEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BC03549E"));
    }
    public static com.kingdee.eas.fdc.basedata.ITargetDescEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.ITargetDescEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BC03549E"));
    }
}