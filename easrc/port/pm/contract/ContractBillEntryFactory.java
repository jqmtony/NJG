package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillEntryFactory
{
    private ContractBillEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("82F47499") ,com.kingdee.eas.port.pm.contract.IContractBillEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.IContractBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("82F47499") ,com.kingdee.eas.port.pm.contract.IContractBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("82F47499"));
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("82F47499"));
    }
}