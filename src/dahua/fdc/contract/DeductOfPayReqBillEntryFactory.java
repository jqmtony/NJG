package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DeductOfPayReqBillEntryFactory
{
    private DeductOfPayReqBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IDeductOfPayReqBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDeductOfPayReqBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("97C05080") ,com.kingdee.eas.fdc.contract.IDeductOfPayReqBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IDeductOfPayReqBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDeductOfPayReqBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("97C05080") ,com.kingdee.eas.fdc.contract.IDeductOfPayReqBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IDeductOfPayReqBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDeductOfPayReqBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("97C05080"));
    }
    public static com.kingdee.eas.fdc.contract.IDeductOfPayReqBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDeductOfPayReqBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("97C05080"));
    }
}