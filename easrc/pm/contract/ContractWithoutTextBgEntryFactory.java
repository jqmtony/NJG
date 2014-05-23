package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractWithoutTextBgEntryFactory
{
    private ContractWithoutTextBgEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.IContractWithoutTextBgEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractWithoutTextBgEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9074D72A") ,com.kingdee.eas.port.pm.contract.IContractWithoutTextBgEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.IContractWithoutTextBgEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractWithoutTextBgEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9074D72A") ,com.kingdee.eas.port.pm.contract.IContractWithoutTextBgEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.IContractWithoutTextBgEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractWithoutTextBgEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9074D72A"));
    }
    public static com.kingdee.eas.port.pm.contract.IContractWithoutTextBgEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractWithoutTextBgEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9074D72A"));
    }
}