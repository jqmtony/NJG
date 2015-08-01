package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConPayPlanDetailFactory
{
    private ConPayPlanDetailFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanDetail getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanDetail)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CD46E04F") ,com.kingdee.eas.fdc.finance.IConPayPlanDetail.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IConPayPlanDetail getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanDetail)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CD46E04F") ,com.kingdee.eas.fdc.finance.IConPayPlanDetail.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanDetail getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanDetail)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CD46E04F"));
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanDetail getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanDetail)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CD46E04F"));
    }
}