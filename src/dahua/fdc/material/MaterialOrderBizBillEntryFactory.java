package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialOrderBizBillEntryFactory
{
    private MaterialOrderBizBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IMaterialOrderBizBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialOrderBizBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("3F1DF459") ,com.kingdee.eas.fdc.material.IMaterialOrderBizBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.material.IMaterialOrderBizBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialOrderBizBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("3F1DF459") ,com.kingdee.eas.fdc.material.IMaterialOrderBizBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IMaterialOrderBizBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialOrderBizBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("3F1DF459"));
    }
    public static com.kingdee.eas.fdc.material.IMaterialOrderBizBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialOrderBizBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("3F1DF459"));
    }
}