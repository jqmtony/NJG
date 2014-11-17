package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractWithoutTextBudgetEntryFactory
{
    private ContractWithoutTextBudgetEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.IContractWithoutTextBudgetEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractWithoutTextBudgetEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6EE9CDAA") ,com.kingdee.eas.port.pm.contract.IContractWithoutTextBudgetEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.IContractWithoutTextBudgetEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractWithoutTextBudgetEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6EE9CDAA") ,com.kingdee.eas.port.pm.contract.IContractWithoutTextBudgetEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.IContractWithoutTextBudgetEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractWithoutTextBudgetEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6EE9CDAA"));
    }
    public static com.kingdee.eas.port.pm.contract.IContractWithoutTextBudgetEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractWithoutTextBudgetEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6EE9CDAA"));
    }
}