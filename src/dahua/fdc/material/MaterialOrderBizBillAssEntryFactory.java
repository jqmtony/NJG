package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialOrderBizBillAssEntryFactory
{
    private MaterialOrderBizBillAssEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IMaterialOrderBizBillAssEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialOrderBizBillAssEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("639E3D2A") ,com.kingdee.eas.fdc.material.IMaterialOrderBizBillAssEntry.class);
    }
    
    public static com.kingdee.eas.fdc.material.IMaterialOrderBizBillAssEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialOrderBizBillAssEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("639E3D2A") ,com.kingdee.eas.fdc.material.IMaterialOrderBizBillAssEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IMaterialOrderBizBillAssEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialOrderBizBillAssEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("639E3D2A"));
    }
    public static com.kingdee.eas.fdc.material.IMaterialOrderBizBillAssEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialOrderBizBillAssEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("639E3D2A"));
    }
}