package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class GuerdonBillFactory
{
    private GuerdonBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IGuerdonBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IGuerdonBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B7408454") ,com.kingdee.eas.fdc.contract.IGuerdonBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IGuerdonBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IGuerdonBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B7408454") ,com.kingdee.eas.fdc.contract.IGuerdonBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IGuerdonBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IGuerdonBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B7408454"));
    }
    public static com.kingdee.eas.fdc.contract.IGuerdonBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IGuerdonBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B7408454"));
    }
}