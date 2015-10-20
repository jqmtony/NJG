package com.kingdee.eas.fdc.contract.settle;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SettleDeclarationBillEntryFactory
{
    private SettleDeclarationBillEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7D67211E") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7D67211E") ,com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7D67211E"));
    }
    public static com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.settle.ISettleDeclarationBillEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7D67211E"));
    }
}