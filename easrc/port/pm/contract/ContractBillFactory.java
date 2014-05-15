package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillFactory
{
    private ContractBillFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.IContractBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7936D359") ,com.kingdee.eas.port.pm.contract.IContractBill.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.IContractBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7936D359") ,com.kingdee.eas.port.pm.contract.IContractBill.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.IContractBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7936D359"));
    }
    public static com.kingdee.eas.port.pm.contract.IContractBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7936D359"));
    }
}