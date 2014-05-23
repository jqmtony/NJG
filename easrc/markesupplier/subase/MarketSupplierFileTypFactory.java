package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierFileTypFactory
{
    private MarketSupplierFileTypFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierFileTyp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierFileTyp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8BFECB51") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierFileTyp.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierFileTyp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierFileTyp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8BFECB51") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierFileTyp.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierFileTyp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierFileTyp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8BFECB51"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierFileTyp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierFileTyp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8BFECB51"));
    }
}