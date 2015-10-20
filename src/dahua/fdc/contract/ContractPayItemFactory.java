package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractPayItemFactory
{
    private ContractPayItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractPayItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractPayItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("967CFACE") ,com.kingdee.eas.fdc.contract.IContractPayItem.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractPayItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractPayItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("967CFACE") ,com.kingdee.eas.fdc.contract.IContractPayItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractPayItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractPayItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("967CFACE"));
    }
    public static com.kingdee.eas.fdc.contract.IContractPayItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractPayItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("967CFACE"));
    }
}