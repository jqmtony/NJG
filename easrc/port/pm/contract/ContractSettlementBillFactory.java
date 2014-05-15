package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractSettlementBillFactory
{
    private ContractSettlementBillFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.IContractSettlementBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractSettlementBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C4D695A2") ,com.kingdee.eas.port.pm.contract.IContractSettlementBill.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.IContractSettlementBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractSettlementBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C4D695A2") ,com.kingdee.eas.port.pm.contract.IContractSettlementBill.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.IContractSettlementBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractSettlementBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C4D695A2"));
    }
    public static com.kingdee.eas.port.pm.contract.IContractSettlementBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractSettlementBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C4D695A2"));
    }
}