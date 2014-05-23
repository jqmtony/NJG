package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierAttachListFactory
{
    private MarketSupplierAttachListFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachList getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachList)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("853A8D61") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachList.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachList getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachList)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("853A8D61") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachList.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachList getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachList)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("853A8D61"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachList getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierAttachList)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("853A8D61"));
    }
}