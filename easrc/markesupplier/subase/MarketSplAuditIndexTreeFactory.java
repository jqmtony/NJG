package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSplAuditIndexTreeFactory
{
    private MarketSplAuditIndexTreeFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndexTree getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndexTree)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D7AEC952") ,com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndexTree.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndexTree getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndexTree)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D7AEC952") ,com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndexTree.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndexTree getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndexTree)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D7AEC952"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndexTree getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSplAuditIndexTree)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D7AEC952"));
    }
}