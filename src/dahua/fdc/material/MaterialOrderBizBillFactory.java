package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialOrderBizBillFactory
{
    private MaterialOrderBizBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IMaterialOrderBizBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialOrderBizBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("458C7B99") ,com.kingdee.eas.fdc.material.IMaterialOrderBizBill.class);
    }
    
    public static com.kingdee.eas.fdc.material.IMaterialOrderBizBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialOrderBizBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("458C7B99") ,com.kingdee.eas.fdc.material.IMaterialOrderBizBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IMaterialOrderBizBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialOrderBizBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("458C7B99"));
    }
    public static com.kingdee.eas.fdc.material.IMaterialOrderBizBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialOrderBizBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("458C7B99"));
    }
}