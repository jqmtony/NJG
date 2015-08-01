package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConPayPlanDataAFactory
{
    private ConPayPlanDataAFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanDataA getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanDataA)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7A3A4619") ,com.kingdee.eas.fdc.finance.IConPayPlanDataA.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IConPayPlanDataA getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanDataA)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7A3A4619") ,com.kingdee.eas.fdc.finance.IConPayPlanDataA.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanDataA getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanDataA)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7A3A4619"));
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanDataA getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanDataA)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7A3A4619"));
    }
}