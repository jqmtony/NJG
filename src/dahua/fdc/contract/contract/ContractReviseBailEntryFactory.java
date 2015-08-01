package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractReviseBailEntryFactory
{
    private ContractReviseBailEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractReviseBailEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractReviseBailEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("327651CB") ,com.kingdee.eas.fdc.contract.IContractReviseBailEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractReviseBailEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractReviseBailEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("327651CB") ,com.kingdee.eas.fdc.contract.IContractReviseBailEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractReviseBailEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractReviseBailEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("327651CB"));
    }
    public static com.kingdee.eas.fdc.contract.IContractReviseBailEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractReviseBailEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("327651CB"));
    }
}