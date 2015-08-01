package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConChangeNoCostSplitEntryFactory
{
    private ConChangeNoCostSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IConChangeNoCostSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeNoCostSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B84B1F5D") ,com.kingdee.eas.fdc.contract.IConChangeNoCostSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IConChangeNoCostSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeNoCostSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B84B1F5D") ,com.kingdee.eas.fdc.contract.IConChangeNoCostSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IConChangeNoCostSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeNoCostSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B84B1F5D"));
    }
    public static com.kingdee.eas.fdc.contract.IConChangeNoCostSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeNoCostSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B84B1F5D"));
    }
}