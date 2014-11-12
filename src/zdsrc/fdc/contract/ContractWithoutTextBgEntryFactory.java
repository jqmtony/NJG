package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractWithoutTextBgEntryFactory
{
    private ContractWithoutTextBgEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractWithoutTextBgEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWithoutTextBgEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A970AA85") ,com.kingdee.eas.fdc.contract.IContractWithoutTextBgEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractWithoutTextBgEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWithoutTextBgEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A970AA85") ,com.kingdee.eas.fdc.contract.IContractWithoutTextBgEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractWithoutTextBgEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWithoutTextBgEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A970AA85"));
    }
    public static com.kingdee.eas.fdc.contract.IContractWithoutTextBgEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWithoutTextBgEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A970AA85"));
    }
}