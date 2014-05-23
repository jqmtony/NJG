package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierAttachListTreeFactory
{
    private MarketSupplierAttachListTreeFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachListTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachListTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("20AADC9F") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachListTree.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachListTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachListTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("20AADC9F") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachListTree.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachListTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachListTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("20AADC9F"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachListTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachListTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("20AADC9F"));
    }
}