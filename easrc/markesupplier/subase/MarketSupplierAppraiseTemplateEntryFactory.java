package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierAppraiseTemplateEntryFactory
{
    private MarketSupplierAppraiseTemplateEntryFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B8B6A7CF") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateEntry.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B8B6A7CF") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B8B6A7CF"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B8B6A7CF"));
    }
}