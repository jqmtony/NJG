package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractSignPlanEntryFactory
{
    private ContractSignPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractSignPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSignPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("52AF323F") ,com.kingdee.eas.fdc.contract.IContractSignPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractSignPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSignPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("52AF323F") ,com.kingdee.eas.fdc.contract.IContractSignPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractSignPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSignPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("52AF323F"));
    }
    public static com.kingdee.eas.fdc.contract.IContractSignPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractSignPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("52AF323F"));
    }
}