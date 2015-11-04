package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillSplitEntryFactory
{
    private ContractBillSplitEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillSplitEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillSplitEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EA36AA8C") ,com.kingdee.eas.fdc.contract.IContractBillSplitEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillSplitEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillSplitEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EA36AA8C") ,com.kingdee.eas.fdc.contract.IContractBillSplitEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillSplitEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillSplitEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EA36AA8C"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillSplitEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillSplitEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EA36AA8C"));
    }
}