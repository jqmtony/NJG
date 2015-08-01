package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PartAOfPayReqBillFactory
{
    private PartAOfPayReqBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPartAOfPayReqBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPartAOfPayReqBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("FE27AA27") ,com.kingdee.eas.fdc.contract.IPartAOfPayReqBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPartAOfPayReqBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPartAOfPayReqBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("FE27AA27") ,com.kingdee.eas.fdc.contract.IPartAOfPayReqBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPartAOfPayReqBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPartAOfPayReqBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("FE27AA27"));
    }
    public static com.kingdee.eas.fdc.contract.IPartAOfPayReqBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPartAOfPayReqBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("FE27AA27"));
    }
}