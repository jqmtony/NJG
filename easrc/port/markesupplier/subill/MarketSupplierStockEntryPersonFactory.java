package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockEntryPersonFactory
{
    private MarketSupplierStockEntryPersonFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryPerson getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryPerson)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EC25A6D9") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryPerson.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryPerson getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryPerson)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EC25A6D9") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryPerson.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryPerson getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryPerson)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EC25A6D9"));
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryPerson getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryPerson)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EC25A6D9"));
    }
}