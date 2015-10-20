package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DeductOfPayReqBillFactory
{
    private DeductOfPayReqBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IDeductOfPayReqBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDeductOfPayReqBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1E8CE312") ,com.kingdee.eas.fdc.contract.IDeductOfPayReqBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IDeductOfPayReqBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDeductOfPayReqBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1E8CE312") ,com.kingdee.eas.fdc.contract.IDeductOfPayReqBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IDeductOfPayReqBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDeductOfPayReqBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1E8CE312"));
    }
    public static com.kingdee.eas.fdc.contract.IDeductOfPayReqBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IDeductOfPayReqBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1E8CE312"));
    }
}