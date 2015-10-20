package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractExecInfosFactory
{
    private ContractExecInfosFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractExecInfos getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractExecInfos)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E97B39A7") ,com.kingdee.eas.fdc.contract.IContractExecInfos.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractExecInfos getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractExecInfos)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E97B39A7") ,com.kingdee.eas.fdc.contract.IContractExecInfos.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractExecInfos getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractExecInfos)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E97B39A7"));
    }
    public static com.kingdee.eas.fdc.contract.IContractExecInfos getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractExecInfos)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E97B39A7"));
    }
}