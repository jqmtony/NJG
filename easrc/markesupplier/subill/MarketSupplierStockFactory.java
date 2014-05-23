package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockFactory
{
    private MarketSupplierStockFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStock getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStock)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5ABA1AAE") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStock.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStock getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStock)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5ABA1AAE") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStock.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStock getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStock)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5ABA1AAE"));
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStock getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStock)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5ABA1AAE"));
    }
}