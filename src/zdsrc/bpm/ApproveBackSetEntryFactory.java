package com.kingdee.eas.bpm;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ApproveBackSetEntryFactory
{
    private ApproveBackSetEntryFactory()
    {
    }
    public static com.kingdee.eas.bpm.IApproveBackSetEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.bpm.IApproveBackSetEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("03385A43") ,com.kingdee.eas.bpm.IApproveBackSetEntry.class);
    }
    
    public static com.kingdee.eas.bpm.IApproveBackSetEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.bpm.IApproveBackSetEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("03385A43") ,com.kingdee.eas.bpm.IApproveBackSetEntry.class, objectCtx);
    }
    public static com.kingdee.eas.bpm.IApproveBackSetEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.bpm.IApproveBackSetEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("03385A43"));
    }
    public static com.kingdee.eas.bpm.IApproveBackSetEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.bpm.IApproveBackSetEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("03385A43"));
    }
}