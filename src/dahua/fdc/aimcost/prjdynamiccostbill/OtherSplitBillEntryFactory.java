package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OtherSplitBillEntryFactory
{
    private OtherSplitBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D4A79BFB") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D4A79BFB") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D4A79BFB"));
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D4A79BFB"));
    }
}