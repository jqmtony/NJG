package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialEnterPlanBillFactory
{
    private MaterialEnterPlanBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IMaterialEnterPlanBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterPlanBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6F1D905B") ,com.kingdee.eas.fdc.material.IMaterialEnterPlanBill.class);
    }
    
    public static com.kingdee.eas.fdc.material.IMaterialEnterPlanBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterPlanBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6F1D905B") ,com.kingdee.eas.fdc.material.IMaterialEnterPlanBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IMaterialEnterPlanBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterPlanBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6F1D905B"));
    }
    public static com.kingdee.eas.fdc.material.IMaterialEnterPlanBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterPlanBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6F1D905B"));
    }
}