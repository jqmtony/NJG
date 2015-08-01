package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialEnterPlanEntryFactory
{
    private MaterialEnterPlanEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IMaterialEnterPlanEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterPlanEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("74C1269E") ,com.kingdee.eas.fdc.material.IMaterialEnterPlanEntry.class);
    }
    
    public static com.kingdee.eas.fdc.material.IMaterialEnterPlanEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterPlanEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("74C1269E") ,com.kingdee.eas.fdc.material.IMaterialEnterPlanEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IMaterialEnterPlanEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterPlanEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("74C1269E"));
    }
    public static com.kingdee.eas.fdc.material.IMaterialEnterPlanEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterPlanEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("74C1269E"));
    }
}