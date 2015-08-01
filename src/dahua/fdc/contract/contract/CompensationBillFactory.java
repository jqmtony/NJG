package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CompensationBillFactory
{
    private CompensationBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ICompensationBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ICompensationBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("40116BBC") ,com.kingdee.eas.fdc.contract.ICompensationBill.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ICompensationBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ICompensationBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("40116BBC") ,com.kingdee.eas.fdc.contract.ICompensationBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ICompensationBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ICompensationBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("40116BBC"));
    }
    public static com.kingdee.eas.fdc.contract.ICompensationBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ICompensationBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("40116BBC"));
    }
}