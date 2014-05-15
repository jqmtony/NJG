package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractWithoutTextFactory
{
    private ContractWithoutTextFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.IContractWithoutText getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractWithoutText)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8AB4E683") ,com.kingdee.eas.port.pm.contract.IContractWithoutText.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.IContractWithoutText getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractWithoutText)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8AB4E683") ,com.kingdee.eas.port.pm.contract.IContractWithoutText.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.IContractWithoutText getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractWithoutText)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8AB4E683"));
    }
    public static com.kingdee.eas.port.pm.contract.IContractWithoutText getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractWithoutText)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8AB4E683"));
    }
}