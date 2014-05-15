package com.kingdee.eas.port.pm.fi;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayRequestBillConfirmEntryFactory
{
    private PayRequestBillConfirmEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillConfirmEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillConfirmEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("315220AF") ,com.kingdee.eas.port.pm.fi.IPayRequestBillConfirmEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillConfirmEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillConfirmEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("315220AF") ,com.kingdee.eas.port.pm.fi.IPayRequestBillConfirmEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillConfirmEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillConfirmEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("315220AF"));
    }
    public static com.kingdee.eas.port.pm.fi.IPayRequestBillConfirmEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.fi.IPayRequestBillConfirmEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("315220AF"));
    }
}