package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConChangeNoCostSplitFactory
{
    private ConChangeNoCostSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IConChangeNoCostSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeNoCostSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E7C97E15") ,com.kingdee.eas.fdc.contract.IConChangeNoCostSplit.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IConChangeNoCostSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeNoCostSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E7C97E15") ,com.kingdee.eas.fdc.contract.IConChangeNoCostSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IConChangeNoCostSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeNoCostSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E7C97E15"));
    }
    public static com.kingdee.eas.fdc.contract.IConChangeNoCostSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeNoCostSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E7C97E15"));
    }
}