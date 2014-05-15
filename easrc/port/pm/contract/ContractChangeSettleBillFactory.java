package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractChangeSettleBillFactory
{
    private ContractChangeSettleBillFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.IContractChangeSettleBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractChangeSettleBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("442D6414") ,com.kingdee.eas.port.pm.contract.IContractChangeSettleBill.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.IContractChangeSettleBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractChangeSettleBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("442D6414") ,com.kingdee.eas.port.pm.contract.IContractChangeSettleBill.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.IContractChangeSettleBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractChangeSettleBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("442D6414"));
    }
    public static com.kingdee.eas.port.pm.contract.IContractChangeSettleBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractChangeSettleBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("442D6414"));
    }
}