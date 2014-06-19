package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynamicCostControlFacadeFactory
{
    private DynamicCostControlFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IDynamicCostControlFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDynamicCostControlFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EE4CE446") ,com.kingdee.eas.fdc.contract.IDynamicCostControlFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IDynamicCostControlFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDynamicCostControlFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EE4CE446") ,com.kingdee.eas.fdc.contract.IDynamicCostControlFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IDynamicCostControlFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDynamicCostControlFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EE4CE446"));
    }
    public static com.kingdee.eas.fdc.contract.IDynamicCostControlFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDynamicCostControlFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EE4CE446"));
    }
}