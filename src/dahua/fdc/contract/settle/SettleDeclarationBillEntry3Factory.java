package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettleDeclarationBillEntry3Factory
{
    private SettleDeclarationBillEntry3Factory()
    {
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry3 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry3)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2F7D02D5") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry3.class);
    }
    
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry3 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry3)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2F7D02D5") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry3.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry3 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry3)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2F7D02D5"));
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry3 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry3)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2F7D02D5"));
    }
}