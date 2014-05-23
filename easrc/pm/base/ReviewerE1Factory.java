package com.kingdee.eas.port.pm.base;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ReviewerE1Factory
{
    private ReviewerE1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.base.IReviewerE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IReviewerE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("0DA05C52") ,com.kingdee.eas.port.pm.base.IReviewerE1.class);
    }
    
    public static com.kingdee.eas.port.pm.base.IReviewerE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IReviewerE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("0DA05C52") ,com.kingdee.eas.port.pm.base.IReviewerE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.base.IReviewerE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IReviewerE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("0DA05C52"));
    }
    public static com.kingdee.eas.port.pm.base.IReviewerE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.base.IReviewerE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("0DA05C52"));
    }
}