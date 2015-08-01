package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractChangeEntryFactory
{
    private ContractChangeEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("509C1015") ,com.kingdee.eas.fdc.contract.IContractChangeEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractChangeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("509C1015") ,com.kingdee.eas.fdc.contract.IContractChangeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("509C1015"));
    }
    public static com.kingdee.eas.fdc.contract.IContractChangeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractChangeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("509C1015"));
    }
}