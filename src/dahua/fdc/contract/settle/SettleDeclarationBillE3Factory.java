package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettleDeclarationBillE3Factory
{
    private SettleDeclarationBillE3Factory()
    {
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE3 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE3)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("73E69342") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE3.class);
    }
    
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE3 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE3)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("73E69342") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE3.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE3 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE3)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("73E69342"));
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE3 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillE3)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("73E69342"));
    }
}