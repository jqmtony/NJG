package com.kingdee.eas.port.markesupplier.subill;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierStockEntryAttFactory
{
    private MarketSupplierStockEntryAttFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryAtt getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryAtt)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4192BD9D") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryAtt.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryAtt getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryAtt)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4192BD9D") ,com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryAtt.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryAtt getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryAtt)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4192BD9D"));
    }
    public static com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryAtt getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subill.IMarketSupplierStockEntryAtt)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4192BD9D"));
    }
}