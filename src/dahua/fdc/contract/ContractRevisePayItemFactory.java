package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractRevisePayItemFactory
{
    private ContractRevisePayItemFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractRevisePayItem getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRevisePayItem)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("10E2D076") ,com.kingdee.eas.fdc.contract.IContractRevisePayItem.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractRevisePayItem getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRevisePayItem)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("10E2D076") ,com.kingdee.eas.fdc.contract.IContractRevisePayItem.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractRevisePayItem getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRevisePayItem)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("10E2D076"));
    }
    public static com.kingdee.eas.fdc.contract.IContractRevisePayItem getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractRevisePayItem)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("10E2D076"));
    }
}