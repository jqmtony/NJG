package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PartAConfmOfPayReqBillFactory
{
    private PartAConfmOfPayReqBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPartAConfmOfPayReqBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPartAConfmOfPayReqBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("1369848A") ,com.kingdee.eas.fdc.contract.IPartAConfmOfPayReqBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPartAConfmOfPayReqBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPartAConfmOfPayReqBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("1369848A") ,com.kingdee.eas.fdc.contract.IPartAConfmOfPayReqBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPartAConfmOfPayReqBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPartAConfmOfPayReqBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("1369848A"));
    }
    public static com.kingdee.eas.fdc.contract.IPartAConfmOfPayReqBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPartAConfmOfPayReqBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("1369848A"));
    }
}