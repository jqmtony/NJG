package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractDemoEntryFactory
{
    private ContractDemoEntryFactory()
    {
    }
    public static com.kingdee.eas.bpmdemo.IContractDemoEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractDemoEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("94D8F6DF") ,com.kingdee.eas.bpmdemo.IContractDemoEntry.class);
    }
    
    public static com.kingdee.eas.bpmdemo.IContractDemoEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractDemoEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("94D8F6DF") ,com.kingdee.eas.bpmdemo.IContractDemoEntry.class, objectCtx);
    }
    public static com.kingdee.eas.bpmdemo.IContractDemoEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractDemoEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("94D8F6DF"));
    }
    public static com.kingdee.eas.bpmdemo.IContractDemoEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractDemoEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("94D8F6DF"));
    }
}