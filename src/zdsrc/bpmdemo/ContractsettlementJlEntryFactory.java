package com.kingdee.eas.bpmdemo;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractsettlementJlEntryFactory
{
    private ContractsettlementJlEntryFactory()
    {
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementJlEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementJlEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("254FF997") ,com.kingdee.eas.bpmdemo.IContractsettlementJlEntry.class);
    }
    
    public static com.kingdee.eas.bpmdemo.IContractsettlementJlEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementJlEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("254FF997") ,com.kingdee.eas.bpmdemo.IContractsettlementJlEntry.class, objectCtx);
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementJlEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementJlEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("254FF997"));
    }
    public static com.kingdee.eas.bpmdemo.IContractsettlementJlEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpmdemo.IContractsettlementJlEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("254FF997"));
    }
}