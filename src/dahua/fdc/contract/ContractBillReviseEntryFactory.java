package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillReviseEntryFactory
{
    private ContractBillReviseEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReviseEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReviseEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("12AECFC6") ,com.kingdee.eas.fdc.contract.IContractBillReviseEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillReviseEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReviseEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("12AECFC6") ,com.kingdee.eas.fdc.contract.IContractBillReviseEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReviseEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReviseEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("12AECFC6"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillReviseEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillReviseEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("12AECFC6"));
    }
}