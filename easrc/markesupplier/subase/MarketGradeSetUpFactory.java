package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketGradeSetUpFactory
{
    private MarketGradeSetUpFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketGradeSetUp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketGradeSetUp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("599904F8") ,com.kingdee.eas.port.markesupplier.subase.IMarketGradeSetUp.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketGradeSetUp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketGradeSetUp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("599904F8") ,com.kingdee.eas.port.markesupplier.subase.IMarketGradeSetUp.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketGradeSetUp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketGradeSetUp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("599904F8"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketGradeSetUp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketGradeSetUp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("599904F8"));
    }
}