package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class OtherSplitBillFactory
{
    private OtherSplitBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("245D16B7") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBill.class);
    }
    
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("245D16B7") ,com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("245D16B7"));
    }
    public static com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.IOtherSplitBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("245D16B7"));
    }
}