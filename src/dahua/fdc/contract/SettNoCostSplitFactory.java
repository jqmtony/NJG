package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettNoCostSplitFactory
{
    private SettNoCostSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ISettNoCostSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettNoCostSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2E637F3F") ,com.kingdee.eas.fdc.contract.ISettNoCostSplit.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ISettNoCostSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettNoCostSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2E637F3F") ,com.kingdee.eas.fdc.contract.ISettNoCostSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ISettNoCostSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettNoCostSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2E637F3F"));
    }
    public static com.kingdee.eas.fdc.contract.ISettNoCostSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISettNoCostSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2E637F3F"));
    }
}