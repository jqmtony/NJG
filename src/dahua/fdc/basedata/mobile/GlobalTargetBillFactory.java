package com.kingdee.eas.fdc.basedata.mobile;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GlobalTargetBillFactory
{
    private GlobalTargetBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("07D1D10F") ,com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetBill.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("07D1D10F") ,com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("07D1D10F"));
    }
    public static com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.mobile.IGlobalTargetBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("07D1D10F"));
    }
}