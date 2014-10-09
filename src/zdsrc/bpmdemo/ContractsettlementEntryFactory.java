package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractsettlementEntryFactory
{
    private ContractsettlementEntryFactory()
    {
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("57ADA379") ,com.kingdee.eas.bpmdemo.IContractsettlementEntry.class);
    }
    
    public static com.kingdee.eas.bpmdemo.IContractsettlementEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("57ADA379") ,com.kingdee.eas.bpmdemo.IContractsettlementEntry.class, objectCtx);
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("57ADA379"));
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("57ADA379"));
    }
}