package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompensationOfPayReqBillFactory
{
    private CompensationOfPayReqBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ICompensationOfPayReqBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ICompensationOfPayReqBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("67F4B7E9") ,com.kingdee.eas.fdc.contract.ICompensationOfPayReqBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ICompensationOfPayReqBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ICompensationOfPayReqBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("67F4B7E9") ,com.kingdee.eas.fdc.contract.ICompensationOfPayReqBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ICompensationOfPayReqBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ICompensationOfPayReqBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("67F4B7E9"));
    }
    public static com.kingdee.eas.fdc.contract.ICompensationOfPayReqBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ICompensationOfPayReqBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("67F4B7E9"));
    }
}