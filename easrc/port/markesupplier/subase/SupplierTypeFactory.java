package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SupplierTypeFactory
{
    private SupplierTypeFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.ISupplierType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.ISupplierType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F592CCBC") ,com.kingdee.eas.port.markesupplier.subase.ISupplierType.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.ISupplierType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.ISupplierType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F592CCBC") ,com.kingdee.eas.port.markesupplier.subase.ISupplierType.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.ISupplierType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.ISupplierType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F592CCBC"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.ISupplierType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.ISupplierType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F592CCBC"));
    }
}