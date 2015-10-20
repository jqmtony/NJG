package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBaseDataFactory
{
    private ContractBaseDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBaseData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBaseData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("82098988") ,com.kingdee.eas.fdc.contract.IContractBaseData.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBaseData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBaseData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("82098988") ,com.kingdee.eas.fdc.contract.IContractBaseData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBaseData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBaseData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("82098988"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBaseData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBaseData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("82098988"));
    }
}