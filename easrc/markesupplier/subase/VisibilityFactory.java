package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class VisibilityFactory
{
    private VisibilityFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IVisibility getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IVisibility)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("88E36628") ,com.kingdee.eas.port.markesupplier.subase.IVisibility.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IVisibility getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IVisibility)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("88E36628") ,com.kingdee.eas.port.markesupplier.subase.IVisibility.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IVisibility getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IVisibility)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("88E36628"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IVisibility getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IVisibility)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("88E36628"));
    }
}