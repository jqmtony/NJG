package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialEnterSumFactory
{
    private MaterialEnterSumFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IMaterialEnterSum getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterSum)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("842589E0") ,com.kingdee.eas.fdc.material.IMaterialEnterSum.class);
    }
    
    public static com.kingdee.eas.fdc.material.IMaterialEnterSum getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterSum)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("842589E0") ,com.kingdee.eas.fdc.material.IMaterialEnterSum.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IMaterialEnterSum getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterSum)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("842589E0"));
    }
    public static com.kingdee.eas.fdc.material.IMaterialEnterSum getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterSum)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("842589E0"));
    }
}