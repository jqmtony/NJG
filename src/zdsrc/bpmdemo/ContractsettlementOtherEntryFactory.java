package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractsettlementOtherEntryFactory
{
    private ContractsettlementOtherEntryFactory()
    {
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementOtherEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementOtherEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CF22B49B") ,com.kingdee.eas.bpmdemo.IContractsettlementOtherEntry.class);
    }
    
    public static com.kingdee.eas.bpmdemo.IContractsettlementOtherEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementOtherEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CF22B49B") ,com.kingdee.eas.bpmdemo.IContractsettlementOtherEntry.class, objectCtx);
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementOtherEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementOtherEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CF22B49B"));
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementOtherEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementOtherEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CF22B49B"));
    }
}