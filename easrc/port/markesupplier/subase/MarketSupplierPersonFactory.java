package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarketSupplierPersonFactory
{
    private MarketSupplierPersonFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierPerson getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierPerson)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F456DF33") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierPerson.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierPerson getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierPerson)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F456DF33") ,com.kingdee.eas.port.markesupplier.subase.IMarketSupplierPerson.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierPerson getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierPerson)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F456DF33"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarketSupplierPerson getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarketSupplierPerson)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F456DF33"));
    }
}