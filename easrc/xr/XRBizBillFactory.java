package com.kingdee.eas.xr;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class XRBizBillFactory
{
    private XRBizBillFactory()
    {
    }
    public static com.kingdee.eas.xr.IXRBizBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.xr.IXRBizBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("765CC124") ,com.kingdee.eas.xr.IXRBizBill.class);
    }
    
    public static com.kingdee.eas.xr.IXRBizBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.xr.IXRBizBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("765CC124") ,com.kingdee.eas.xr.IXRBizBill.class, objectCtx);
    }
    public static com.kingdee.eas.xr.IXRBizBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.xr.IXRBizBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("765CC124"));
    }
    public static com.kingdee.eas.xr.IXRBizBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.xr.IXRBizBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("765CC124"));
    }
}