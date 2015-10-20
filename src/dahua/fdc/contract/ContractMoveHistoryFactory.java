package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractMoveHistoryFactory
{
    private ContractMoveHistoryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractMoveHistory getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractMoveHistory)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F3BCC0F6") ,com.kingdee.eas.fdc.contract.IContractMoveHistory.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractMoveHistory getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractMoveHistory)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F3BCC0F6") ,com.kingdee.eas.fdc.contract.IContractMoveHistory.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractMoveHistory getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractMoveHistory)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F3BCC0F6"));
    }
    public static com.kingdee.eas.fdc.contract.IContractMoveHistory getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractMoveHistory)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F3BCC0F6"));
    }
}