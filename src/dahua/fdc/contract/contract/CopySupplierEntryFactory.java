package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class CopySupplierEntryFactory
{
    private CopySupplierEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ICopySupplierEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ICopySupplierEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("169D3FB6") ,com.kingdee.eas.fdc.contract.ICopySupplierEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ICopySupplierEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ICopySupplierEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("169D3FB6") ,com.kingdee.eas.fdc.contract.ICopySupplierEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ICopySupplierEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ICopySupplierEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("169D3FB6"));
    }
    public static com.kingdee.eas.fdc.contract.ICopySupplierEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ICopySupplierEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("169D3FB6"));
    }
}