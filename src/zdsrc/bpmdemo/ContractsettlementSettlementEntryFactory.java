package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractsettlementSettlementEntryFactory
{
    private ContractsettlementSettlementEntryFactory()
    {
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementSettlementEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementSettlementEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("DD239F10") ,com.kingdee.eas.bpmdemo.IContractsettlementSettlementEntry.class);
    }
    
    public static com.kingdee.eas.bpmdemo.IContractsettlementSettlementEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementSettlementEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("DD239F10") ,com.kingdee.eas.bpmdemo.IContractsettlementSettlementEntry.class, objectCtx);
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementSettlementEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementSettlementEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("DD239F10"));
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementSettlementEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementSettlementEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("DD239F10"));
    }
}