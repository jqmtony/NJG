package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractProgrammingEntryFactory
{
    private ContractProgrammingEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractProgrammingEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractProgrammingEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("21118986") ,com.kingdee.eas.fdc.contract.IContractProgrammingEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractProgrammingEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractProgrammingEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("21118986") ,com.kingdee.eas.fdc.contract.IContractProgrammingEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractProgrammingEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractProgrammingEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("21118986"));
    }
    public static com.kingdee.eas.fdc.contract.IContractProgrammingEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractProgrammingEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("21118986"));
    }
}