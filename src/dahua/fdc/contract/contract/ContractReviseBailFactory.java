package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractReviseBailFactory
{
    private ContractReviseBailFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractReviseBail getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractReviseBail)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1707FEE7") ,com.kingdee.eas.fdc.contract.IContractReviseBail.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractReviseBail getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractReviseBail)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1707FEE7") ,com.kingdee.eas.fdc.contract.IContractReviseBail.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractReviseBail getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractReviseBail)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1707FEE7"));
    }
    public static com.kingdee.eas.fdc.contract.IContractReviseBail getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractReviseBail)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1707FEE7"));
    }
}