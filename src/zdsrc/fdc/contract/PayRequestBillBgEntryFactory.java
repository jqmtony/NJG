package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayRequestBillBgEntryFactory
{
    private PayRequestBillBgEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPayRequestBillBgEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBillBgEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BAA74684") ,com.kingdee.eas.fdc.contract.IPayRequestBillBgEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPayRequestBillBgEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBillBgEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BAA74684") ,com.kingdee.eas.fdc.contract.IPayRequestBillBgEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPayRequestBillBgEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBillBgEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BAA74684"));
    }
    public static com.kingdee.eas.fdc.contract.IPayRequestBillBgEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPayRequestBillBgEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BAA74684"));
    }
}