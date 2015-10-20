package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettleDeclarationBillFactory
{
    private SettleDeclarationBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("19B1A7B4") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("19B1A7B4") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("19B1A7B4"));
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("19B1A7B4"));
    }
}