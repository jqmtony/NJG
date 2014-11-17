package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillBudgetEntryFactory
{
    private ContractBillBudgetEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillBudgetEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillBudgetEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("48BC9F14") ,com.kingdee.eas.port.pm.contract.IContractBillBudgetEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.IContractBillBudgetEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillBudgetEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("48BC9F14") ,com.kingdee.eas.port.pm.contract.IContractBillBudgetEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillBudgetEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillBudgetEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("48BC9F14"));
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillBudgetEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillBudgetEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("48BC9F14"));
    }
}