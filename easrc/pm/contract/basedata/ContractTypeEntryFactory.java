package com.kingdee.eas.port.pm.contract.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractTypeEntryFactory
{
    private ContractTypeEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IContractTypeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractTypeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("81D6F919") ,com.kingdee.eas.port.pm.contract.basedata.IContractTypeEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.basedata.IContractTypeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractTypeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("81D6F919") ,com.kingdee.eas.port.pm.contract.basedata.IContractTypeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IContractTypeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractTypeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("81D6F919"));
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IContractTypeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractTypeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("81D6F919"));
    }
}