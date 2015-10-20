package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettleDeclarationBillE1Factory
{
    private SettleDeclarationBillE1Factory()
    {
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("73E69340") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE1.class);
    }
    
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("73E69340") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE1.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("73E69340"));
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("73E69340"));
    }
}