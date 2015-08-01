package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DynaticFundPlanRptFacadeFactory
{
    private DynaticFundPlanRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IDynaticFundPlanRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDynaticFundPlanRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("20B5786B") ,com.kingdee.eas.fdc.finance.IDynaticFundPlanRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IDynaticFundPlanRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDynaticFundPlanRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("20B5786B") ,com.kingdee.eas.fdc.finance.IDynaticFundPlanRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IDynaticFundPlanRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDynaticFundPlanRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("20B5786B"));
    }
    public static com.kingdee.eas.fdc.finance.IDynaticFundPlanRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IDynaticFundPlanRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("20B5786B"));
    }
}