package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PcTypeEntryFactory
{
    private PcTypeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IPcTypeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPcTypeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("48D46D75") ,com.kingdee.eas.fdc.contract.programming.IPcTypeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IPcTypeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPcTypeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("48D46D75") ,com.kingdee.eas.fdc.contract.programming.IPcTypeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IPcTypeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPcTypeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("48D46D75"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IPcTypeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IPcTypeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("48D46D75"));
    }
}