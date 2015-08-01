package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierContentEntryFactory
{
    private SupplierContentEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ISupplierContentEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISupplierContentEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0F7EDA20") ,com.kingdee.eas.fdc.contract.ISupplierContentEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ISupplierContentEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISupplierContentEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0F7EDA20") ,com.kingdee.eas.fdc.contract.ISupplierContentEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ISupplierContentEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISupplierContentEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0F7EDA20"));
    }
    public static com.kingdee.eas.fdc.contract.ISupplierContentEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ISupplierContentEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0F7EDA20"));
    }
}