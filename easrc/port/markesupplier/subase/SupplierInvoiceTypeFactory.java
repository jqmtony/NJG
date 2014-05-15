package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierInvoiceTypeFactory
{
    private SupplierInvoiceTypeFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BFCCD445") ,com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceType.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BFCCD445") ,com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceType.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BFCCD445"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BFCCD445"));
    }
}