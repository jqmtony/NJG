package com.kingdee.eas.port.pm.contract.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractTypeInviteTypeEntryFactory
{
    private ContractTypeInviteTypeEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IContractTypeInviteTypeEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractTypeInviteTypeEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B7365436") ,com.kingdee.eas.port.pm.contract.basedata.IContractTypeInviteTypeEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.basedata.IContractTypeInviteTypeEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractTypeInviteTypeEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B7365436") ,com.kingdee.eas.port.pm.contract.basedata.IContractTypeInviteTypeEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IContractTypeInviteTypeEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractTypeInviteTypeEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B7365436"));
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IContractTypeInviteTypeEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IContractTypeInviteTypeEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B7365436"));
    }
}