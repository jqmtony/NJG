package com.kingdee.eas.bpm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ApproveBackSetFactory
{
    private ApproveBackSetFactory()
    {
    }
    public static com.kingdee.eas.bpm.IApproveBackSet getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpm.IApproveBackSet)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("91D08B6F") ,com.kingdee.eas.bpm.IApproveBackSet.class);
    }
    
    public static com.kingdee.eas.bpm.IApproveBackSet getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpm.IApproveBackSet)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("91D08B6F") ,com.kingdee.eas.bpm.IApproveBackSet.class, objectCtx);
    }
    public static com.kingdee.eas.bpm.IApproveBackSet getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpm.IApproveBackSet)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("91D08B6F"));
    }
    public static com.kingdee.eas.bpm.IApproveBackSet getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpm.IApproveBackSet)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("91D08B6F"));
    }
}