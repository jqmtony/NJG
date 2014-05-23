package com.kingdee.eas.port.pm.contract.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractTypeContractWFTypeEntryFactory
{
    private ContractTypeContractWFTypeEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IContractTypeContractWFTypeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractTypeContractWFTypeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A079FB5E") ,com.kingdee.eas.port.pm.contract.basedata.IContractTypeContractWFTypeEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.basedata.IContractTypeContractWFTypeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractTypeContractWFTypeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A079FB5E") ,com.kingdee.eas.port.pm.contract.basedata.IContractTypeContractWFTypeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IContractTypeContractWFTypeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractTypeContractWFTypeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A079FB5E"));
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IContractTypeContractWFTypeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractTypeContractWFTypeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A079FB5E"));
    }
}