package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierAppraiseTemplateFactory
{
    private MarketSupplierAppraiseTemplateFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplate getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplate)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("71A2F663") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplate.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplate getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplate)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("71A2F663") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplate.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplate getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplate)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("71A2F663"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplate getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAppraiseTemplate)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("71A2F663"));
    }
}