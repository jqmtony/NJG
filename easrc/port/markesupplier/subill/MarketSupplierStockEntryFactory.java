package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockEntryFactory
{
    private MarketSupplierStockEntryFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D4717A64") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntry.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D4717A64") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D4717A64"));
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D4717A64"));
    }
}