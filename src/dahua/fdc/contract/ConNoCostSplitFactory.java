package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConNoCostSplitFactory
{
    private ConNoCostSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IConNoCostSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConNoCostSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("10334EA5") ,com.kingdee.eas.fdc.contract.IConNoCostSplit.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IConNoCostSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConNoCostSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("10334EA5") ,com.kingdee.eas.fdc.contract.IConNoCostSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IConNoCostSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConNoCostSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("10334EA5"));
    }
    public static com.kingdee.eas.fdc.contract.IConNoCostSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConNoCostSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("10334EA5"));
    }
}