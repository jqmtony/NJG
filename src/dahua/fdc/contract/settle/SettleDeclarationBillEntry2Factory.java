package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettleDeclarationBillEntry2Factory
{
    private SettleDeclarationBillEntry2Factory()
    {
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("2F7D02D4") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry2.class);
    }
    
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("2F7D02D4") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry2.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("2F7D02D4"));
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("2F7D02D4"));
    }
}