package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OtherSplitNewEntryFactory
{
    private OtherSplitNewEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitNewEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitNewEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("09BD8D62") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitNewEntry.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitNewEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitNewEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("09BD8D62") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitNewEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitNewEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitNewEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("09BD8D62"));
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitNewEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitNewEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("09BD8D62"));
    }
}