package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSplAuditIndexFactory
{
    private MarketSplAuditIndexFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndex getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndex)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B1BECB94") ,com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndex.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndex getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndex)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B1BECB94") ,com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndex.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndex getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndex)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B1BECB94"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndex getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndex)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B1BECB94"));
    }
}