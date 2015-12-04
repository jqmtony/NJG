package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PcontractTrackBillFactory
{
    private PcontractTrackBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPcontractTrackBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPcontractTrackBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F049860B") ,com.kingdee.eas.fdc.contract.IPcontractTrackBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPcontractTrackBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPcontractTrackBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F049860B") ,com.kingdee.eas.fdc.contract.IPcontractTrackBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPcontractTrackBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPcontractTrackBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F049860B"));
    }
    public static com.kingdee.eas.fdc.contract.IPcontractTrackBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPcontractTrackBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F049860B"));
    }
}