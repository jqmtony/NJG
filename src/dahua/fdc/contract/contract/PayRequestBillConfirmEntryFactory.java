package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayRequestBillConfirmEntryFactory
{
    private PayRequestBillConfirmEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPayRequestBillConfirmEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBillConfirmEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CF84109B") ,com.kingdee.eas.fdc.contract.IPayRequestBillConfirmEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPayRequestBillConfirmEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBillConfirmEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CF84109B") ,com.kingdee.eas.fdc.contract.IPayRequestBillConfirmEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPayRequestBillConfirmEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBillConfirmEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CF84109B"));
    }
    public static com.kingdee.eas.fdc.contract.IPayRequestBillConfirmEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBillConfirmEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CF84109B"));
    }
}