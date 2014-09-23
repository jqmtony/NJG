package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractDemoFactory
{
    private ContractDemoFactory()
    {
    }
    public static com.kingdee.eas.bpmdemo.IContractDemo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractDemo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E7EF7D53") ,com.kingdee.eas.bpmdemo.IContractDemo.class);
    }
    
    public static com.kingdee.eas.bpmdemo.IContractDemo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractDemo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E7EF7D53") ,com.kingdee.eas.bpmdemo.IContractDemo.class, objectCtx);
    }
    public static com.kingdee.eas.bpmdemo.IContractDemo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractDemo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E7EF7D53"));
    }
    public static com.kingdee.eas.bpmdemo.IContractDemo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractDemo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E7EF7D53"));
    }
}