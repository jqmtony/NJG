package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingContractFxbdEntryFactory
{
    private ProgrammingContractFxbdEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContractFxbdEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContractFxbdEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6B7634A3") ,com.kingdee.eas.fdc.contract.programming.IProgrammingContractFxbdEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContractFxbdEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContractFxbdEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6B7634A3") ,com.kingdee.eas.fdc.contract.programming.IProgrammingContractFxbdEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContractFxbdEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContractFxbdEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6B7634A3"));
    }
    public static com.kingdee.eas.fdc.contract.programming.IProgrammingContractFxbdEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.programming.IProgrammingContractFxbdEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6B7634A3"));
    }
}