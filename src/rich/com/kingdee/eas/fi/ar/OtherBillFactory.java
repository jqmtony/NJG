package com.kingdee.eas.fi.ar;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OtherBillFactory
{
    private OtherBillFactory()
    {
    }
    public static com.kingdee.eas.fi.ar.IOtherBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fi.ar.IOtherBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FC910EF3") ,com.kingdee.eas.fi.ar.IOtherBill.class);
    }
    
    public static com.kingdee.eas.fi.ar.IOtherBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fi.ar.IOtherBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FC910EF3") ,com.kingdee.eas.fi.ar.IOtherBill.class, objectCtx);
    }
    public static com.kingdee.eas.fi.ar.IOtherBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fi.ar.IOtherBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FC910EF3"));
    }
    public static com.kingdee.eas.fi.ar.IOtherBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fi.ar.IOtherBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FC910EF3"));
    }
}