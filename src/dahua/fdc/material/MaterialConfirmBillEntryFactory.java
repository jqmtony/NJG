package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialConfirmBillEntryFactory
{
    private MaterialConfirmBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IMaterialConfirmBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialConfirmBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("05E62E58") ,com.kingdee.eas.fdc.material.IMaterialConfirmBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.material.IMaterialConfirmBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialConfirmBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("05E62E58") ,com.kingdee.eas.fdc.material.IMaterialConfirmBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IMaterialConfirmBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialConfirmBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("05E62E58"));
    }
    public static com.kingdee.eas.fdc.material.IMaterialConfirmBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialConfirmBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("05E62E58"));
    }
}