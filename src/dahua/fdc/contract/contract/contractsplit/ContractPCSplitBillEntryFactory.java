package com.kingdee.eas.fdc.contract.contractsplit;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractPCSplitBillEntryFactory
{
    private ContractPCSplitBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("50C9B837") ,com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("50C9B837") ,com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("50C9B837"));
    }
    public static com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("50C9B837"));
    }
}