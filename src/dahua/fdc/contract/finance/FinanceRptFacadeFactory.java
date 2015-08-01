package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FinanceRptFacadeFactory
{
    private FinanceRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IFinanceRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFinanceRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("17BD1285") ,com.kingdee.eas.fdc.finance.IFinanceRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IFinanceRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFinanceRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("17BD1285") ,com.kingdee.eas.fdc.finance.IFinanceRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IFinanceRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFinanceRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("17BD1285"));
    }
    public static com.kingdee.eas.fdc.finance.IFinanceRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IFinanceRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("17BD1285"));
    }
}