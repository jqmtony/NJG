package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBailFactory
{
    private ContractBailFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractBail getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBail)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0D6DB38F") ,com.kingdee.eas.fdc.contract.IContractBail.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractBail getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBail)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0D6DB38F") ,com.kingdee.eas.fdc.contract.IContractBail.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractBail getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBail)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0D6DB38F"));
    }
    public static com.kingdee.eas.fdc.contract.IContractBail getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractBail)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0D6DB38F"));
    }
}