package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConNoCostSplitEntryFactory
{
    private ConNoCostSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IConNoCostSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConNoCostSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4F3D28CD") ,com.kingdee.eas.fdc.contract.IConNoCostSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IConNoCostSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConNoCostSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4F3D28CD") ,com.kingdee.eas.fdc.contract.IConNoCostSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IConNoCostSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConNoCostSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4F3D28CD"));
    }
    public static com.kingdee.eas.fdc.contract.IConNoCostSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConNoCostSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4F3D28CD"));
    }
}