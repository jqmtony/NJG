package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialConfirmBillFactory
{
    private MaterialConfirmBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IMaterialConfirmBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialConfirmBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1D79BE3A") ,com.kingdee.eas.fdc.material.IMaterialConfirmBill.class);
    }
    
    public static com.kingdee.eas.fdc.material.IMaterialConfirmBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialConfirmBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1D79BE3A") ,com.kingdee.eas.fdc.material.IMaterialConfirmBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IMaterialConfirmBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialConfirmBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1D79BE3A"));
    }
    public static com.kingdee.eas.fdc.material.IMaterialConfirmBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialConfirmBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1D79BE3A"));
    }
}