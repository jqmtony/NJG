package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractRevLandDeveloperFactory
{
    private ContractRevLandDeveloperFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractRevLandDeveloper getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRevLandDeveloper)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("16FF78A9") ,com.kingdee.eas.fdc.contract.IContractRevLandDeveloper.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractRevLandDeveloper getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRevLandDeveloper)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("16FF78A9") ,com.kingdee.eas.fdc.contract.IContractRevLandDeveloper.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractRevLandDeveloper getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRevLandDeveloper)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("16FF78A9"));
    }
    public static com.kingdee.eas.fdc.contract.IContractRevLandDeveloper getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRevLandDeveloper)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("16FF78A9"));
    }
}