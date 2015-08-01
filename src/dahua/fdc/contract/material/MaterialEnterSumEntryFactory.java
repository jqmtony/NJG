package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialEnterSumEntryFactory
{
    private MaterialEnterSumEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IMaterialEnterSumEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterSumEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9525BA72") ,com.kingdee.eas.fdc.material.IMaterialEnterSumEntry.class);
    }
    
    public static com.kingdee.eas.fdc.material.IMaterialEnterSumEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterSumEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9525BA72") ,com.kingdee.eas.fdc.material.IMaterialEnterSumEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IMaterialEnterSumEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterSumEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9525BA72"));
    }
    public static com.kingdee.eas.fdc.material.IMaterialEnterSumEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialEnterSumEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9525BA72"));
    }
}