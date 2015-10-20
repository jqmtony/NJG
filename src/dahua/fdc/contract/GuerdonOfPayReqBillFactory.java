package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GuerdonOfPayReqBillFactory
{
    private GuerdonOfPayReqBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IGuerdonOfPayReqBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IGuerdonOfPayReqBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B32DB881") ,com.kingdee.eas.fdc.contract.IGuerdonOfPayReqBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IGuerdonOfPayReqBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IGuerdonOfPayReqBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B32DB881") ,com.kingdee.eas.fdc.contract.IGuerdonOfPayReqBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IGuerdonOfPayReqBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IGuerdonOfPayReqBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B32DB881"));
    }
    public static com.kingdee.eas.fdc.contract.IGuerdonOfPayReqBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IGuerdonOfPayReqBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B32DB881"));
    }
}