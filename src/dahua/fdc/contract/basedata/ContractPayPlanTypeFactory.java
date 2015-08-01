package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractPayPlanTypeFactory
{
    private ContractPayPlanTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IContractPayPlanType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractPayPlanType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FF502627") ,com.kingdee.eas.fdc.basedata.IContractPayPlanType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IContractPayPlanType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractPayPlanType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FF502627") ,com.kingdee.eas.fdc.basedata.IContractPayPlanType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IContractPayPlanType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractPayPlanType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FF502627"));
    }
    public static com.kingdee.eas.fdc.basedata.IContractPayPlanType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IContractPayPlanType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FF502627"));
    }
}