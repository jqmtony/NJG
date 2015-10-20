package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillEntryFactory
{
    private ContractBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("97EB5EDE") ,com.kingdee.eas.fdc.contract.IContractBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("97EB5EDE") ,com.kingdee.eas.fdc.contract.IContractBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("97EB5EDE"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("97EB5EDE"));
    }
}