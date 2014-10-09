package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractsettlementAssEntryFactory
{
    private ContractsettlementAssEntryFactory()
    {
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementAssEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementAssEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9C4ABA0A") ,com.kingdee.eas.bpmdemo.IContractsettlementAssEntry.class);
    }
    
    public static com.kingdee.eas.bpmdemo.IContractsettlementAssEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementAssEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9C4ABA0A") ,com.kingdee.eas.bpmdemo.IContractsettlementAssEntry.class, objectCtx);
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementAssEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementAssEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9C4ABA0A"));
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementAssEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementAssEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9C4ABA0A"));
    }
}