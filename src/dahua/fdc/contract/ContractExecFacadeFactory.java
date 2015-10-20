package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractExecFacadeFactory
{
    private ContractExecFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractExecFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractExecFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4014ACB8") ,com.kingdee.eas.fdc.contract.IContractExecFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractExecFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractExecFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4014ACB8") ,com.kingdee.eas.fdc.contract.IContractExecFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractExecFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractExecFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4014ACB8"));
    }
    public static com.kingdee.eas.fdc.contract.IContractExecFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractExecFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4014ACB8"));
    }
}