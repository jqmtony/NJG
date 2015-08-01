package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractLandDeveloperFactory
{
    private ContractLandDeveloperFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractLandDeveloper getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractLandDeveloper)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("963F1AD2") ,com.kingdee.eas.fdc.contract.IContractLandDeveloper.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractLandDeveloper getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractLandDeveloper)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("963F1AD2") ,com.kingdee.eas.fdc.contract.IContractLandDeveloper.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractLandDeveloper getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractLandDeveloper)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("963F1AD2"));
    }
    public static com.kingdee.eas.fdc.contract.IContractLandDeveloper getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractLandDeveloper)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("963F1AD2"));
    }
}