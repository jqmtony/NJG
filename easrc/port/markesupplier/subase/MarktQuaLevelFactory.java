package com.kingdee.eas.port.markesupplier.subase;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MarktQuaLevelFactory
{
    private MarktQuaLevelFactory()
    {
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarktQuaLevel getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarktQuaLevel)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("061CC058") ,com.kingdee.eas.port.markesupplier.subase.IMarktQuaLevel.class);
    }
    
    public static com.kingdee.eas.port.markesupplier.subase.IMarktQuaLevel getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarktQuaLevel)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("061CC058") ,com.kingdee.eas.port.markesupplier.subase.IMarktQuaLevel.class, objectCtx);
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarktQuaLevel getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarktQuaLevel)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("061CC058"));
    }
    public static com.kingdee.eas.port.markesupplier.subase.IMarktQuaLevel getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.markesupplier.subase.IMarktQuaLevel)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("061CC058"));
    }
}