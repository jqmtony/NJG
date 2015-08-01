package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBailEntryFactory
{
    private ContractBailEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBailEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBailEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BA2F4623") ,com.kingdee.eas.fdc.contract.IContractBailEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBailEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBailEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BA2F4623") ,com.kingdee.eas.fdc.contract.IContractBailEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBailEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBailEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BA2F4623"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBailEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBailEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BA2F4623"));
    }
}