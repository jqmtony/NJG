package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierAppraiseTemplateTreeFactory
{
    private MarketSupplierAppraiseTemplateTreeFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("60D2F4A1") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateTree.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("60D2F4A1") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateTree.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("60D2F4A1"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplateTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("60D2F4A1"));
    }
}