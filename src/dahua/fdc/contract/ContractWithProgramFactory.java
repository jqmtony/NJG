package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractWithProgramFactory
{
    private ContractWithProgramFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractWithProgram getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWithProgram)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D05CF851") ,com.kingdee.eas.fdc.contract.IContractWithProgram.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractWithProgram getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWithProgram)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D05CF851") ,com.kingdee.eas.fdc.contract.IContractWithProgram.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractWithProgram getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWithProgram)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D05CF851"));
    }
    public static com.kingdee.eas.fdc.contract.IContractWithProgram getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractWithProgram)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D05CF851"));
    }
}