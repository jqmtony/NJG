package com.kingdee.eas.fdc.contract.contractsplit;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractPCSplitBillFactory
{
    private ContractPCSplitBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8FB144FB") ,com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8FB144FB") ,com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8FB144FB"));
    }
    public static com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.contractsplit.IContractPCSplitBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8FB144FB"));
    }
}