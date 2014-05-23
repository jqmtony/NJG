package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractChangeSettleBillEntryFactory
{
    private ContractChangeSettleBillEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.IContractChangeSettleBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractChangeSettleBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1AB220BE") ,com.kingdee.eas.port.pm.contract.IContractChangeSettleBillEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.IContractChangeSettleBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractChangeSettleBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1AB220BE") ,com.kingdee.eas.port.pm.contract.IContractChangeSettleBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.IContractChangeSettleBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractChangeSettleBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1AB220BE"));
    }
    public static com.kingdee.eas.port.pm.contract.IContractChangeSettleBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractChangeSettleBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1AB220BE"));
    }
}