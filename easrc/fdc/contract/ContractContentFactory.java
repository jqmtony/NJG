package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractContentFactory
{
    private ContractContentFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractContent getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractContent)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FE28236C") ,com.kingdee.eas.fdc.contract.IContractContent.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractContent getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractContent)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FE28236C") ,com.kingdee.eas.fdc.contract.IContractContent.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractContent getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractContent)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FE28236C"));
    }
    public static com.kingdee.eas.fdc.contract.IContractContent getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractContent)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FE28236C"));
    }
}