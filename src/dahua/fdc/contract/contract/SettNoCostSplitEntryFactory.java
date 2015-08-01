package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettNoCostSplitEntryFactory
{
    private SettNoCostSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ISettNoCostSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettNoCostSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7E754873") ,com.kingdee.eas.fdc.contract.ISettNoCostSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ISettNoCostSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettNoCostSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7E754873") ,com.kingdee.eas.fdc.contract.ISettNoCostSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ISettNoCostSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettNoCostSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7E754873"));
    }
    public static com.kingdee.eas.fdc.contract.ISettNoCostSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettNoCostSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7E754873"));
    }
}