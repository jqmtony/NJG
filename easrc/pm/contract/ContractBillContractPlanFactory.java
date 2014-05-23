package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillContractPlanFactory
{
    private ContractBillContractPlanFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillContractPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillContractPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F8532154") ,com.kingdee.eas.port.pm.contract.IContractBillContractPlan.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.IContractBillContractPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillContractPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F8532154") ,com.kingdee.eas.port.pm.contract.IContractBillContractPlan.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillContractPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillContractPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F8532154"));
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillContractPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillContractPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F8532154"));
    }
}