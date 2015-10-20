package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettleDeclarationBillE2Factory
{
    private SettleDeclarationBillE2Factory()
    {
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE2 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE2)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("73E69341") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE2.class);
    }
    
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE2 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE2)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("73E69341") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE2.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE2 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE2)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("73E69341"));
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE2 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE2)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("73E69341"));
    }
}