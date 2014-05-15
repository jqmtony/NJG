package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReviewerFactory
{
    private ReviewerFactory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IReviewer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IReviewer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("970E8D46") ,com.kingdee.eas.port.pm.base.IReviewer.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IReviewer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IReviewer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("970E8D46") ,com.kingdee.eas.port.pm.base.IReviewer.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IReviewer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IReviewer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("970E8D46"));
    }
    public static com.kingdee.eas.port.pm.base.IReviewer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IReviewer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("970E8D46"));
    }
}