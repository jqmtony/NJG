package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynamicCostDiffFacadeFactory
{
    private DynamicCostDiffFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IDynamicCostDiffFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDynamicCostDiffFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C290D370") ,com.kingdee.eas.fdc.contract.IDynamicCostDiffFacade.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IDynamicCostDiffFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDynamicCostDiffFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C290D370") ,com.kingdee.eas.fdc.contract.IDynamicCostDiffFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IDynamicCostDiffFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDynamicCostDiffFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C290D370"));
    }
    public static com.kingdee.eas.fdc.contract.IDynamicCostDiffFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDynamicCostDiffFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C290D370"));
    }
}