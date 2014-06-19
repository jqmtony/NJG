package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractWebServiceFacadeFactory
{
    private ContractWebServiceFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractWebServiceFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWebServiceFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E3EE6728") ,com.kingdee.eas.fdc.contract.IContractWebServiceFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractWebServiceFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWebServiceFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E3EE6728") ,com.kingdee.eas.fdc.contract.IContractWebServiceFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractWebServiceFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWebServiceFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E3EE6728"));
    }
    public static com.kingdee.eas.fdc.contract.IContractWebServiceFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWebServiceFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E3EE6728"));
    }
}