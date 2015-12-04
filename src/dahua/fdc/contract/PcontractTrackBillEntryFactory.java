package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PcontractTrackBillEntryFactory
{
    private PcontractTrackBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPcontractTrackBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPcontractTrackBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("957FA127") ,com.kingdee.eas.fdc.contract.IPcontractTrackBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPcontractTrackBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPcontractTrackBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("957FA127") ,com.kingdee.eas.fdc.contract.IPcontractTrackBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPcontractTrackBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPcontractTrackBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("957FA127"));
    }
    public static com.kingdee.eas.fdc.contract.IPcontractTrackBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPcontractTrackBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("957FA127"));
    }
}