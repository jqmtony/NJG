package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillReviseFactory
{
    private ContractBillReviseFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBillRevise getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillRevise)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7B31700C") ,com.kingdee.eas.fdc.contract.IContractBillRevise.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBillRevise getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillRevise)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7B31700C") ,com.kingdee.eas.fdc.contract.IContractBillRevise.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBillRevise getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillRevise)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7B31700C"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBillRevise getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBillRevise)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7B31700C"));
    }
}