package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConPayPlanDataFactory
{
    private ConPayPlanDataFactory()
    {
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanData getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanData)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("24F99F28") ,com.kingdee.eas.fdc.finance.IConPayPlanData.class);
    }
    
    public static com.kingdee.eas.fdc.finance.IConPayPlanData getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanData)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("24F99F28") ,com.kingdee.eas.fdc.finance.IConPayPlanData.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanData getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanData)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("24F99F28"));
    }
    public static com.kingdee.eas.fdc.finance.IConPayPlanData getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.finance.IConPayPlanData)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("24F99F28"));
    }
}