package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractModelFactory
{
    private ContractModelFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractModel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractModel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A0EA0ADC") ,com.kingdee.eas.fdc.contract.IContractModel.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractModel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractModel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A0EA0ADC") ,com.kingdee.eas.fdc.contract.IContractModel.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractModel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractModel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A0EA0ADC"));
    }
    public static com.kingdee.eas.fdc.contract.IContractModel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractModel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A0EA0ADC"));
    }
}