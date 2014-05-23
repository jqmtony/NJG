package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillPayItemFactory
{
    private ContractBillPayItemFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillPayItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillPayItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C7930A42") ,com.kingdee.eas.port.pm.contract.IContractBillPayItem.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.IContractBillPayItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillPayItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C7930A42") ,com.kingdee.eas.port.pm.contract.IContractBillPayItem.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillPayItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillPayItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C7930A42"));
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillPayItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillPayItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C7930A42"));
    }
}