package com.kingdee.eas.port.pm.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractBillOtherLandDevelperFactory
{
    private ContractBillOtherLandDevelperFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillOtherLandDevelper getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillOtherLandDevelper)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B793D003") ,com.kingdee.eas.port.pm.contract.IContractBillOtherLandDevelper.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.IContractBillOtherLandDevelper getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillOtherLandDevelper)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B793D003") ,com.kingdee.eas.port.pm.contract.IContractBillOtherLandDevelper.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillOtherLandDevelper getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillOtherLandDevelper)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B793D003"));
    }
    public static com.kingdee.eas.port.pm.contract.IContractBillOtherLandDevelper getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.IContractBillOtherLandDevelper)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B793D003"));
    }
}