package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractCostSplitFactory
{
    private ContractCostSplitFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractCostSplit getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractCostSplit)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1C2F5180") ,com.kingdee.eas.fdc.contract.IContractCostSplit.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractCostSplit getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractCostSplit)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1C2F5180") ,com.kingdee.eas.fdc.contract.IContractCostSplit.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractCostSplit getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractCostSplit)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1C2F5180"));
    }
    public static com.kingdee.eas.fdc.contract.IContractCostSplit getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractCostSplit)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1C2F5180"));
    }
}