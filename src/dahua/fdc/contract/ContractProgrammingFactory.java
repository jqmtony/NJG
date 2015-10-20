package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractProgrammingFactory
{
    private ContractProgrammingFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractProgramming getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractProgramming)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F0F19E4C") ,com.kingdee.eas.fdc.contract.IContractProgramming.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractProgramming getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractProgramming)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F0F19E4C") ,com.kingdee.eas.fdc.contract.IContractProgramming.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractProgramming getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractProgramming)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F0F19E4C"));
    }
    public static com.kingdee.eas.fdc.contract.IContractProgramming getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractProgramming)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F0F19E4C"));
    }
}