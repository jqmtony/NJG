package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockE4Factory
{
    private MarketSupplierStockE4Factory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockE4 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockE4)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("949E2FBD") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockE4.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockE4 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockE4)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("949E2FBD") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockE4.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockE4 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockE4)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("949E2FBD"));
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockE4 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockE4)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("949E2FBD"));
    }
}