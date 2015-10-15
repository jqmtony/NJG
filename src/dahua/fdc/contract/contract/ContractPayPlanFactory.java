package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractPayPlanFactory
{
    private ContractPayPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractPayPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractPayPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("96800AE4") ,com.kingdee.eas.fdc.contract.IContractPayPlan.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractPayPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractPayPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("96800AE4") ,com.kingdee.eas.fdc.contract.IContractPayPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractPayPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractPayPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("96800AE4"));
    }
    public static com.kingdee.eas.fdc.contract.IContractPayPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractPayPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("96800AE4"));
    }
}