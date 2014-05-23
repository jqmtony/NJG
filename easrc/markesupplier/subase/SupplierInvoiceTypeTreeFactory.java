package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierInvoiceTypeTreeFactory
{
    private SupplierInvoiceTypeTreeFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceTypeTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceTypeTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A9371183") ,com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceTypeTree.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceTypeTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceTypeTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A9371183") ,com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceTypeTree.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceTypeTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceTypeTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A9371183"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceTypeTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.ISupplierInvoiceTypeTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A9371183"));
    }
}