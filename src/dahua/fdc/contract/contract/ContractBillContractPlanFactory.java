package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillContractPlanFactory
{
    private ContractBillContractPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillContractPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillContractPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("55520F6F") ,com.kingdee.eas.fdc.contract.IContractBillContractPlan.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillContractPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillContractPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("55520F6F") ,com.kingdee.eas.fdc.contract.IContractBillContractPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillContractPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillContractPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("55520F6F"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillContractPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillContractPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("55520F6F"));
    }
}