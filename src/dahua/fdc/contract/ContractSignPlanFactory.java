package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractSignPlanFactory
{
    private ContractSignPlanFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractSignPlan getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSignPlan)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FB39A5F3") ,com.kingdee.eas.fdc.contract.IContractSignPlan.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractSignPlan getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSignPlan)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FB39A5F3") ,com.kingdee.eas.fdc.contract.IContractSignPlan.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractSignPlan getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSignPlan)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FB39A5F3"));
    }
    public static com.kingdee.eas.fdc.contract.IContractSignPlan getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSignPlan)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FB39A5F3"));
    }
}